package uk.co.malavalli.server;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.junit.Assert;
import org.apache.http.HttpHeaders;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class HttpTestResponse {
	private final HttpResponse response;
	private final String responseString;
	private Document responseDocument;
	private Map<String, String> defaultNamespaceMap = new HashMap<String, String>();

	public HttpTestResponse(HttpResponse response) throws IllegalStateException, IOException {
		this.response = response;
		responseString = IOUtils.toString(response.getEntity().getContent());

		declareNamespace("sportsml", "http://iptc.org/std/SportsML/2008-04-01/");
		declareNamespace("bbc", "http://www.bbc.co.uk/sport/sports-data");
	}

	public int getStatusCode() {
		return response.getStatusLine().getStatusCode();
	}

	public String getStatusReason() {
		return response.getStatusLine().getReasonPhrase();
	}

	public String getContentType() {
		Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
		if (headers.length > 0) {
			return headers[0].getValue();
		}
		return null;
	}

	public String getCacheControl() {
		Header[] headers = response.getHeaders(HttpHeaders.CACHE_CONTROL);
		if (headers.length > 0) {
			return headers[0].getValue();
		}
		return null;
	}

	public Header[] getHeaders() {
		return response.getAllHeaders();
	}

	public String getString() {
		return responseString;
	}

	public Document getDocument() throws DocumentException {
		if (responseDocument == null) {
			if (responseString == null) {
				throw new DocumentException("Response content is empty, status code is: " + getStatusCode());
			}
			responseDocument = new SAXReader().read(new StringReader(responseString));
		}
		return responseDocument;
	}

	public HttpTestResponse assertStatus(int expected) {
		return assertStatus("Exepected status code " + expected, expected);
	}

	public HttpTestResponse assertStatus(String msg, int expected) {
		Assert.assertEquals(msg, expected, getStatusCode());
		return this;
	}

	public HttpTestResponse assertContains(String expected) throws DocumentException {
		return this.assertContains("Expected substring " + expected, expected);
	}

	public HttpTestResponse assertContains(String msg, String expected) throws DocumentException {
		Assert.assertTrue(msg + "\nresponse:" + getString(), getString().contains(expected));
		return this;
	}

	public HttpTestResponse assertRegex(String expectedRegex) throws DocumentException {
		return assertRegex("Expected regex " + expectedRegex, expectedRegex);
	}

	public HttpTestResponse assertRegex(String msg, String expectedRegex) throws DocumentException {
		Assert.assertTrue(msg, Pattern.matches(expectedRegex, getString()));
		return this;
	}

	public HttpTestResponse assertXPathMatches(String msg, String xpathExpression) throws DocumentException {
		Assert.assertTrue(msg, createXPath(xpathExpression).selectSingleNode(getDocument()) != null);
		return this;
	}

	public HttpTestResponse assertXPathEquals(String xpathExpression, String expected) throws DocumentException {
		return assertXPathEquals("Expected xpath " + xpathExpression, xpathExpression, expected);
	}

	public HttpTestResponse assertXPathEquals(String message, String xpathExpression, String expected) throws DocumentException {
		String node = valueOf(xpathExpression);
		Assert.assertNotNull(message, node);
		Assert.assertEquals(message, expected, node);
		return this;
	}

	public String valueOf(String xpathExpression) throws DocumentException {
		return createXPath(xpathExpression).valueOf(getDocument());
	}

	public Node selectSingleNode(String xpathExpression) throws DocumentException {
		return createXPath(xpathExpression).selectSingleNode(getDocument());
	}

	public List<Node> selectNodes(String xpathExpression) throws DocumentException {
		List<?> nodes = createXPath(xpathExpression).selectNodes(getDocument());
		return Lists.transform(nodes, new Function<Object, Node>() {
			@Override
			public Node apply(@Nullable Object node) {
				return (Node) node;
			}
		});

	}

	public HttpTestResponse assertXPathCount(String xpathExpression, int expectedCount) throws DocumentException {
		return assertXPathCount("Expected XPath " + xpathExpression, xpathExpression, expectedCount);
	}

	public HttpTestResponse assertXPathCount(String msg, String xpathExpression, int expectedCount) throws DocumentException {
		List<?> nodes = selectNodes(xpathExpression);
		int nodeCount = nodes == null ? 0 : nodes.size();
		Assert.assertEquals(msg, expectedCount, nodeCount);
		return this;
	}

	public HttpTestResponse declareNamespace(String prefix, String uri) {
		defaultNamespaceMap.put(prefix, uri);
		return this;
	}

	private XPath createXPath(String xpath) {
		XPath xPath = DocumentHelper.createXPath(xpath);
		xPath.setNamespaceURIs(getDefaultNamespaceMap());
		return xPath;
	}

	public Map<String, String> getDefaultNamespaceMap() {
		return defaultNamespaceMap;
	}

	public void setDefaultNamespaceMap(Map<String, String> defaultNamespaceMap) {
		this.defaultNamespaceMap = defaultNamespaceMap;
	}

}
