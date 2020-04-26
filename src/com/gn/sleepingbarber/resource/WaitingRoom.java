package com.gn.sleepingbarber.resource;

import java.util.LinkedList;
import java.util.Queue;

import com.gn.sleepingbarber.entity.Customer;

public class WaitingRoom implements IWaitingRoom {
	
	// shared queue 
	private Queue<Customer> waitingQueue = new LinkedList<>();
	
	// a lock 
	
	// lock condition to wait and signal 

	@Override
	public void add(Customer customer) {
		// acquire a lock
		// await when queue is full
		
		// add customer on queue and signalAll thread(barber thread) waiting on queue
		
		// release a lock
	}

	@Override
	public Customer take() {
		// acquire a lock
		
		// wait till queue is empty
		
		// get customer from queue and signal all thread(Customer thread) waiting for some space to add customer to queue
		
		// release a lock
		
		return null;
		
	}

}
