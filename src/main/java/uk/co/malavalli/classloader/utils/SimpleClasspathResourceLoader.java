package uk.co.malavalli.classloader.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleClasspathResourceLoader implements ClasspathResourceLoader {

	Logger LOG = LoggerFactory.getLogger(getClass());
	private final ClasspathUtils classpathResourceLoader;

	public SimpleClasspathResourceLoader() {
		classpathResourceLoader = new ClasspathUtils();
	}

	@Override
	public String load(String classpath) {
		try {
			String resourceAsString = classpathResourceLoader.loadResourceAsString(classpath);
			if (resourceAsString == null) {
				LOG.error("Error loading classpath resource: " + classpath);
				throw new ClasspathResourceLoadingException("Error loading file: " + classpath, null);
			}

			LOG.debug("Successfully loaded classpath resource: " + classpath);

			return resourceAsString;
		} catch (IOException e) {
			LOG.error("IO Error loading classpath resource: " + classpath);
			throw new ClasspathResourceLoadingException("Error loading file: " + classpath, e);
		}
	}

}
