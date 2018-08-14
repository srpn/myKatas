package com.train;

import java.util.stream.Collectors;

public class SquareDigit {

	public static void main(String[] args) {
		SquareDigit obj = new SquareDigit();
		int result = obj.squareDigits(9119);
		System.out.println("Pass? "+(811181==result));
		System.out.println(result);
	}

	public int squareDigits(int n) {
		String input = String.valueOf(n);
		String result = input
			.chars()
			.map(Character::getNumericValue)
			.map(i->i*i)
			.mapToObj(String::valueOf)
			.collect(Collectors.joining(""));
		return Integer.parseInt(result);
	}

}
