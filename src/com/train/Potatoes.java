package com.train;

public class Potatoes {

	public static int potatoes(int initialWaterPercent, int initialWeight, int finalWaterPercent) {
		double finalWeight = ((initialWeight - ((initialWaterPercent*initialWeight)/100.0))/(100-finalWaterPercent))*100;
		System.out.println(finalWeight);
		return Math.toIntExact(Math.round(finalWeight));
	}
}
