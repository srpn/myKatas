package com.train;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FindOdd {
	public static int findIt(int[] a) {
		return IntStream.of(a)
			.boxed()
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
			.entrySet().stream()
			.filter(entry -> entry.getValue()%2 != 0)
			.mapToInt(entry -> entry.getKey())
			.findFirst()
			.getAsInt();
	}
}