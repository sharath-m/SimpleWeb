package uk.co.malavalli.server;

import java.net.BindException;
import java.net.URL;

import javax.annotation.PreDestroy;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class IntegrationTestServer {

	private static final int DEFAULT_MAX_PORT_INCREMENT = 10;
	private static final Logger Log = LoggerFactory.getLogger(IntegrationTestServer.class);
	private final JettyServerFactory serverFactory;
	private Server jettyServer;
	private URL baseUrl = null;
	private final HttpClient httpClient;
	private int portNumber;
	private int maxPortNumber;

	public IntegrationTestServer(int portNumber, HttpClient httpClient, JettyServerFactory serverFactory) throws Exception {
		this.setPortNumber(portNumber);
		this.setMaxPortNumber(portNumber + DEFAULT_MAX_PORT_INCREMENT);
		this.httpClient = httpClient;
		this.serverFactory = serverFactory;
	}

	public void start() throws Exception {
		if (jettyServer == null || !jettyServer.isStarted()) {
			attemptStart();
		}
	}

	private void attemptStart() throws Exception {
		try {
			if (jettyServer != null) {
				serverFactory.unregisterServer(jettyServer);
			}
			jettyServer = serverFactory.createServer(getPortNumber());
			jettyServer.start();
			Log.debug("Server Started on " + getBaseUrl());
		} catch (BindException ex) {
			if (getPortNumber() < getMaxPortNumber()) {
				Log.debug("Port " + getPortNumber() + " already in use, trying next");
				setPortNumber(getPortNumber() + 1);
				attemptStart();
			} else {
				throw ex;
			}
		} catch (Exception ex) {
			Log.error("error starting jetty server", ex);
			throw ex;
		}

	}

	@PreDestroy
	public void stop() {
		try {
			if (jettyServer != null) {
				serverFactory.unregisterServer(jettyServer);
			}
		} catch (Exception ex) {
			Log.error("Error stopping jetty server", ex);
		}
	}

	public boolean isRunning() {
		return jettyServer != null && jettyServer.isStarted();
	}

	private URL getBaseUrl() throws Exception {
		if (baseUrl == null) {
			start();
			int port = jettyServer.getConnectors()[0].getPort();
			baseUrl = new URL(String.format("http://localhost:%d", port));
		}
		return baseUrl;
	}

	public HttpTestResponse get(String url, String accept) throws Exception {
		HttpGet httpGet = new HttpGet(makeUrl(url).toString());
		httpGet.addHeader("Accept", accept);
		return new HttpTestResponse(httpClient.execute(httpGet));
	}

	public HttpTestResponse put(String url, String content, String contentType) throws Exception {
		HttpPut httpPut = new HttpPut(makeUrl(url).toString());
		httpPut.setHeader("Content-Type", contentType);
		httpPut.setEntity(new StringEntity(content));
		return new HttpTestResponse(httpClient.execute(httpPut));
	}

	public HttpTestResponse post(String url, String content, String contentType) throws Exception {
		HttpPost httpPost = new HttpPost(makeUrl(url).toString());
		httpPost.setHeader("Content-Type", contentType);
		httpPost.setEntity(new StringEntity(content));
		return new HttpTestResponse(httpClient.execute(httpPost));
	}

	public HttpTestResponse delete(String url) throws Exception {
		HttpDelete httpPost = new HttpDelete(makeUrl(url).toString());
		return new HttpTestResponse(httpClient.execute(httpPost));
	}

	private URL makeUrl(String url) throws Exception {
		return new URL(getBaseUrl(), url);
	}

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		if (portNumber != this.portNumber) {
			stop();
			this.portNumber = portNumber;
		}
	}

	public int getMaxPortNumber() {
		return maxPortNumber;
	}

	public void setMaxPortNumber(int maxPortNumber) {
		this.maxPortNumber = maxPortNumber;
	}
}
