package com.train;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.LongStream;

public class Bud {
	
	static String resp = "Nothing";
	volatile static Boolean toContinue = true; 
	public static String buddy(long start, long limit) {
		ExecutorService pool = Executors.newWorkStealingPool(8);
		
		for(long l=start; l<=limit && toContinue; l++) {
			final long l1 = l;
			CompletableFuture.supplyAsync(() -> {
				if(getSumOfDivisors(l1)>start) {
					for(long m=l1+1; m<=(limit*2); m++) {
						if(isBuddyPair(l1, m)) {
							return "("+l1+" "+m+")";
						}
					}
				}
				throw new RuntimeException("Nope");
			}).thenApply(result -> {
				toContinue = false;
				pool.shutdownNow();
				resp = result;
				return result;
			});
		}
		try {
			pool.awaitTermination(30, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}   
	
	public static String buddySlow(long start, long limit) {
		for(long l=start; l<=limit; l++) {
			if(getSumOfDivisors(l)>start) {
				for(long m=l+1; m<=(limit*2); m++) {
					if(isBuddyPair(l, m)) {
						//						System.out.println("Buddy1: "+l+" - sumOfDivisors: "+getSumOfDivisors(l));
						//						System.out.println("Buddy2: "+m+" - sumOfDivisors: "+getSumOfDivisors(m));
						return "("+l+" "+m+")";
					}
				}
			}
		}
		return "Nothing";
	}    

	private static boolean isBuddyPair(long a, long b) {
		if(getSumOfDivisors(a) == b+1 && getSumOfDivisors(b) == a+1) {
			return true;
		}
		return false;
	}

	public static void main(String args[]) {
		System.out.println(getSumOfDivisors(48));
		System.out.println(getSumOfDivisors(75));
		System.out.println(isBuddyPair(48, 75));
	}

	private static Map<Long, Long> sodCache = new HashMap<>();
	private static Function<Long, Long> findSum = (Long n) -> {
		long ans = 1;
		for(int i=2; i<=Math.round(Math.sqrt(n));i++) {
			if(n%i == 0) {
				ans += i;
				long conjugate = n/i;
				if(conjugate != i) {
					ans += conjugate;
				}
			}
		}
		return ans;
	};
	private static long getSumOfDivisors2(long number) {
		return sodCache.computeIfAbsent(number, (Long n) -> { 
			return LongStream.rangeClosed(2, Math.round(Math.sqrt(n)))
					.parallel()
					.filter(i -> n%i==0)
					.map(i -> (n/i==i) ? i : (n/i)+i)
					.sum()+1;
			});
	}
	
	private static long getSumOfDivisors(long num) {
		return sodCache.computeIfAbsent(num, findSum);
	}
}