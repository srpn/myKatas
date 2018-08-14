package com.train;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpinWords {

	public String spinWords(String sentence) {
		return Stream.of(Pattern.compile(" ").split(sentence))
				.map(s -> s.length()>4?(new StringBuilder(s)).reverse().toString():s)
				.collect(Collectors.joining(" "));
	}
}