package com.serverside.dt;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Currency {

	CAD("CAD"), USD("USD");

	private final String value;

	Currency(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	// ⭐ Build a lookup map once (case-insensitive)
	private static final Map<String, Currency> LOOKUP = Stream.of(values())
			.collect(Collectors.toMap(c -> c.value.toUpperCase(), c -> c));

	// ⭐ Public key set for easy testing
	public static final Set<String> KEYS = LOOKUP.keySet();

	// ⭐ Case-insensitive conversion
	public static Currency from(String raw) {
		if (raw == null) {
			throw new IllegalArgumentException("Currency cannot be null");
		}

		Currency c = LOOKUP.get(raw.trim().toUpperCase());
		if (c == null) {
			throw new IllegalArgumentException("Unknown currency: " + raw);
		}

		return c;
	}

	// ⭐ Simple validity check
	public static boolean isValid(String raw) {
		return raw != null && KEYS.contains(raw.trim().toUpperCase());
	}
}
