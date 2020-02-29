package com.gn.mt.sync;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProduserConsuerExampleUsingWaitNotify {

	private static final Logger LOGGER = LogManager.getLogger(ProduserConsuerExampleUsingWaitNotify.class);

	public static final int QUEUE_SIZE = 21;

	public static void main(String[] args) {
		LOGGER.info("Starting main!!");
		
		Queue<Integer> sharedQueue = new LinkedList<>();

		ExecutorService executorSvc = Executors.newFixedThreadPool(10);
		Producer p1 = new Producer(sharedQueue, QUEUE_SIZE);
		Producer p2 = new Producer(sharedQueue, QUEUE_SIZE);
		Producer p3 = new Producer(sharedQueue, QUEUE_SIZE);
		Producer p4 = new Producer(sharedQueue, QUEUE_SIZE);
		
		Consumer c1 = new Consumer(sharedQueue, QUEUE_SIZE);
		Consumer c2 = new Consumer(sharedQueue, QUEUE_SIZE);
		
		Future<String> pf1 = executorSvc.submit(p1);
		Future<String> pf2 = executorSvc.submit(p2);
		Future<String> pf3 = executorSvc.submit(p3);
		Future<String> pf4 = executorSvc.submit(p4);
		
		Future<String> cf1 = executorSvc.submit(c1);
		Future<String> cf2 = executorSvc.submit(c2);
		
		
				try {
					LOGGER.info(pf1.get());
					LOGGER.info(pf2.get());
					LOGGER.info(pf3.get());
					LOGGER.info(pf4.get());
					LOGGER.info(cf1.get());
					LOGGER.info(cf2.get());
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		
		LOGGER.info("Shutting down!!");
		executorSvc.shutdown();

	}

	public static class Producer implements Callable<String> {
		Queue<Integer> sharedQueue;
		int maxSize;

		public Producer(Queue<Integer> sharedQueue, int maxSize) {
			this.sharedQueue = sharedQueue;
			this.maxSize = maxSize;
		}

		@Override
		public String call() throws Exception {
			Random r = new Random();
			int itemProduced = 5;
			while (itemProduced-- > 0) { // loop to produce thread live
				synchronized (sharedQueue) {
					while (sharedQueue.size() == maxSize) {
						sharedQueue.wait();
					}
					Thread.sleep(500);
					int item = r.nextInt(100);
					sharedQueue.offer(item);
					LOGGER.info(String.format("Item produced - %d , QueueSize [%d] ", item,sharedQueue.size()));
					
					sharedQueue.notifyAll();
				}
			}
			LOGGER.info("producer thread completed!!!");
			return "Producer done!!";

		}

	}

	public static class Consumer implements Callable<String> {
		Queue<Integer> sharedQueue;
		int maxSize;

		public Consumer(Queue<Integer> sharedQueue, int maxSize) {
			this.sharedQueue = sharedQueue;
			this.maxSize = maxSize;
		}

		@Override
		public String call() throws Exception {
			int consumedItems = 10;
			while(consumedItems-- > 0) {
				synchronized(sharedQueue) {
					while(sharedQueue.isEmpty()) {
						sharedQueue.wait();
					}
					int item = sharedQueue.poll();
					Thread.sleep(400);
					LOGGER.info(String.format("Item removed - %d, QueueSize [%d] ", item, sharedQueue.size()));
					sharedQueue.notifyAll();
				}
			}
			LOGGER.info("Consumer thread completed!!!");
			return "Consumer done!!";
		}

	}

}
