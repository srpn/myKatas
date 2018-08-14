package com.train;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Worker3 {

	public static class ExecutedTasks {
		public final List<Runnable> successful = new ArrayList<>();
		public final Set<Runnable> failed = new HashSet<>();
		public final Set<Runnable> timedOut = new HashSet<>();	
	}
	
	public ExecutedTasks execute(Collection<Runnable> actions, long timeoutMillis) {
		ExecutedTasks result = new ExecutedTasks();
		ExecutorService pool = Executors.newCachedThreadPool();
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		Map<CompletableFuture<?>, Runnable> taskMap = new HashMap<>();

		Runnable timeoutRunner = () -> {
			System.out.println("Scheduler runs at: "+new Date());
			taskMap.keySet().forEach(cf -> {
				if(!cf.isDone()) {
					cf.cancel(true);
					result.timedOut.add(taskMap.get(cf));
				}
			});
			pool.shutdownNow();
		};

		AtomicInteger numOfThreadsRunning = new AtomicInteger(0);
		final int totalActions = actions.size();

		for(Runnable action : actions) {
			System.out.println("Started "+action.toString());
			numOfThreadsRunning.incrementAndGet();
			CompletableFuture<Void> cf = CompletableFuture.runAsync(action, pool)
					.handle((res, ex) -> {
						if(ex != null) {
							result.failed.add(action);
						} else {
							result.successful.add(action);
						}
						if(taskMap.size() == totalActions && numOfThreadsRunning.decrementAndGet() == 0) { 
							// nothing waiting for scheduler to timeout
							scheduler.shutdownNow();
						}
						return null;
					});
			taskMap.put(cf, action);
		}
		System.out.println("Scheduled at: "+new Date());
		scheduler.schedule(timeoutRunner, timeoutMillis, TimeUnit.MILLISECONDS);
		try {
			scheduler.shutdown();
			scheduler.awaitTermination(timeoutMillis+100, TimeUnit.MILLISECONDS);
			pool.shutdown();
			pool.awaitTermination(timeoutMillis, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}
}