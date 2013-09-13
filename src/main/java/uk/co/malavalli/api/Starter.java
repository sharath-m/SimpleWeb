package uk.co.malavalli.api;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class Starter {
	Logger LOG = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		try {
			String absolutePath = new ClassPathResource("config").getFile().getAbsolutePath();
			System.err.println(absolutePath);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
