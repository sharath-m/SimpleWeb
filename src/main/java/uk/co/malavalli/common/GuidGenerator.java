package uk.co.malavalli.common;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class GuidGenerator {
	public String generate() {
		return UUID.randomUUID().toString();
	}
}
