package com.train;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Worker {

	public static void main(String args[]) {
		Runnable action = new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Running"+Thread.currentThread().getName());
				try {
					Thread.sleep(100);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		Worker worker = new Worker();
		worker.execute(action, 1000);
	}
	
	public void execute(Runnable action, int nTimes) {
		ForkJoinPool pool = new ForkJoinPool(10);
		for (int i =0; i<nTimes; i++)
			pool.submit(action);
		pool.shutdown();
		try {
			pool.awaitTermination(60, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

