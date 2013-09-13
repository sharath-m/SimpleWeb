package uk.co.malavalli.classloader.utils;

public class ConfigurableClasspathResourceLoader implements ClasspathResourceLoader {

	private static final String DEFAULT_CLASSPATH_LOCATION = "/";

	private static final String DEFAULT_EXTENSION = "";

	private String location;

	private String extension;

	private ClasspathResourceLoader classpathResourceLoader;

	public ConfigurableClasspathResourceLoader() {
		location = DEFAULT_CLASSPATH_LOCATION;
		extension = DEFAULT_EXTENSION;
	}

	public void initialise() {
		classpathResourceLoader = new SimpleClasspathResourceLoader();

		// if (cached)
		// this.classpathResourceLoader = new
		// CachedClasspathResourceLoader(this.classpathResourceLoader);
	}

	@Override
	public String load(String filename) {

		if (classpathResourceLoader == null) {
			throw new RuntimeException("ConfigurableClasspathResourceLoader requires initialising via the initialise() method");
		}

		String classpath = location + filename + extension;
		String resource = classpathResourceLoader.load(classpath);

		if (resource == null) {
			throw new ClasspathResourceLoadingException("Error loading file from classpath: " + classpath, null);
		}
		return resource;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}
