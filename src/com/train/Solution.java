package com.train;

import java.util.*;
import java.util.stream.IntStream;

public class Solution {
	public static int predictAge(int age1, int age2, int age3, int age4, int age5, int age6, int age7, int age8) {
		int[] inputAges = {age1, age2, age3, age4, age5, age6, age7, age8};
		int sum = Arrays.stream(inputAges)
				.map(i -> i*i)
				.sum();
		return (int) Math.sqrt(sum)/2;
	}

	public int GetSum(int a, int b)
	{
		if(a == b) {
			return a;
		} else {
			IntStream inputStream;
			if(a>b) {
				inputStream = IntStream.rangeClosed(b, a);
			} else {
				inputStream = IntStream.rangeClosed(a, b);
			}
			return inputStream.sum();
		}
	}

	public static void main(String args[]) {
		Solution hmmm = new Solution();
		System.out.println(hmmm.GetSum(0, -1));
	}
}