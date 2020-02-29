package com.gn.mt.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SynchronizationExample {
	Object lock = new Object();

	public static void main(String[] args) {
		
		SynchronizationExample se = new SynchronizationExample();
		SynchronizationExample se1 = new SynchronizationExample();

		ExecutorService executor = Executors.newFixedThreadPool(2);

		executor.execute(new ThreadOne(se));
		executor.execute(new ThreadOne(se1));

		executor.shutdown();

		try {
			executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * static synchronized method - only one thread would be allowed at a time
	 * @throws InterruptedException
	 */
	public static void m1() throws InterruptedException {
		synchronized (SynchronizationExample.class) {
			Thread.sleep(4000);
			System.out.println(Thread.currentThread() + " - m1()");
		}
	}

	/**
	 * synchronzed block - if lock object is same for two thread, only one would be allowed to enter
	 * @throws InterruptedException
	 */
	public void m2() throws InterruptedException {
		synchronized(lock) {
			Thread.sleep(4000);
			System.out.println(Thread.currentThread() + " - m2()");
		}
	}

	/**
	 * synchronzed method - only one thread will enter at time
	 * @throws InterruptedException
	 */
	public synchronized void m3() throws InterruptedException {
		Thread.sleep(3000);
		System.out.println(Thread.currentThread() + " - m3() sync");
	}
	
	/**
	 * Static synchronization 
	 * @throws InterruptedException
	 */

	public static synchronized void m4() throws InterruptedException {
		Thread.sleep(4000);
		System.out.println(Thread.currentThread() + " - m4() sync");
	}

	public static class ThreadOne implements Runnable {
		SynchronizationExample se;

		public ThreadOne(SynchronizationExample se) {
			this.se = se;
		}

		@Override
		public void run() {
			try {
				se.m1();
				se.m2();
				se.m3();
				se.m4();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
