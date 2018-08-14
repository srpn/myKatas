package com.train;
public class TwoToOne {
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
    public static String longest (String s1, String s2) {
    	int[] sortedDistinct =new StringBuilder(s1)
						    	.append(s2)
						    	.chars()
						    	.distinct()
						    	.sorted()
						    	.toArray();
    	return new String(sortedDistinct, 0, sortedDistinct.length);
    	
    }
}