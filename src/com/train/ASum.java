package com.train;
public class ASum {
	
	public static void main(String[] args) {
		System.out.println(findNb(135440716410000L));
	}
	
	public static long findNb(long m) {
		long i=1;
		long ans = -1;
		long sum = 0;
		while(sum < m) {
			sum += i*i*i;
			if(sum == m) {
				ans = i;
			}
			i++;
		}
		return ans;
	}	
}