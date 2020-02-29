package com.gn.mt.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class VolatileExample {
	
	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newFixedThreadPool(100);
		
		IntStream.range(0, 100).forEach(value -> {
			executor.execute( () -> {
				System.out.println(SingletonClass.getInstance());
			});
		});

		executor.shutdown();
	}
	

	/**
	 * Singleton class in multithreading environment 
	 * @author Gaurav Nigam
	 *
	 */
	public static class SingletonClass {
		
		private SingletonClass () {}
		// Volatile will ensure all read and write happens to main memory
		private volatile static SingletonClass instance;
		
		public static SingletonClass getInstance() {
			
			if(instance == null) {
				synchronized(SingletonClass.class) {
					if(instance == null) {
						System.out.println(Thread.currentThread() + "##################" + instance);
						instance = new SingletonClass();
						System.out.println(Thread.currentThread() + "##################" + instance);
					}
				}
			}
			
			return instance;
			
		}
	}

}
