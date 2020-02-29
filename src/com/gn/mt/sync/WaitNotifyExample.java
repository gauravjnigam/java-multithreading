package com.gn.mt.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Print 1 - LIMIT(defined) sequentially using 3 thread using wait notify 
 * @author Gaurav Nigam
 *
 */
public class WaitNotifyExample {
	
	public static final int LIMIT = 100;

	volatile boolean one = true;
	volatile boolean two = false;
	volatile boolean three = false;
	volatile int count = 0;

	public static void main(String[] args) {

		WaitNotifyExample wne = new WaitNotifyExample();
		ExecutorService executors = Executors.newFixedThreadPool(3);

		// created three threads sharing one object wne (with 3 states)
		executors.execute(new MyRunnable1(wne));
		executors.execute(new MyRunnable2(wne));
		executors.execute(new MyRunnable3(wne));

		executors.shutdown();

	}

	public static class MyRunnable1 implements Runnable {
		WaitNotifyExample wne;

		public MyRunnable1(WaitNotifyExample wne) {
			this.wne = wne;
		}

		@Override
		public void run() {
			while (wne.count < LIMIT) { // main loop to activate thread
				synchronized (wne) {
					while (!wne.one) { // for wait if condition is not true

						try {
							wne.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (wne.count <= LIMIT) {
						System.out.println(String.format("One # %s # %s ", Thread.currentThread(), wne.count++));
					}
					wne.one = false;
					wne.two = true;
					wne.notifyAll();
				}
			}

		}
	}

	public static class MyRunnable2 implements Runnable {
		WaitNotifyExample wne;

		public MyRunnable2(WaitNotifyExample wne) {
			this.wne = wne;
		}

		@Override
		public void run() {
			while (wne.count < LIMIT) {
				synchronized (wne) {
					while (!wne.two) {

						try {
							wne.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (wne.count <= LIMIT) {
						System.out.println(String.format("two # %s # %s ", Thread.currentThread(), wne.count++));
					}
					wne.two = false;
					wne.three = true;
					wne.notifyAll();

				}
			}

		}
	}

	public static class MyRunnable3 implements Runnable {
		WaitNotifyExample wne;

		public MyRunnable3(WaitNotifyExample wne) {
			this.wne = wne;
		}

		@Override
		public void run() {
			while (wne.count < LIMIT) {
				synchronized (wne) {
					while (!wne.three) {
						try {
							wne.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (wne.count <= LIMIT) {
						System.out.println(String.format("three # %s # %s ", Thread.currentThread(), wne.count++));
					}
					wne.three = false;
					wne.one = true;
					wne.notifyAll();
				}
			}

		}
	}
}
