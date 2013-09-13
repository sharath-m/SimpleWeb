package uk.co.malavalli.classloader.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class ClasspathUtils {

	public String loadResourceAsString(String classpath) throws IOException {
		InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(classpath);
		if (resourceAsStream == null) {
			throw new FileNotFoundException();
		}
		return IOUtils.toString(resourceAsStream);
	}
}
