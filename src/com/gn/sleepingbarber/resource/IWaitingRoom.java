package com.gn.sleepingbarber.resource;

import com.gn.sleepingbarber.entity.Customer;

public interface IWaitingRoom {
	
	/**
	 * will be used by customer thread to add into waiting room
	 */
	void add(Customer customer);
	
	/**
	 * will be used by Barber thread to take customer
	 */
	public Customer take();

	

}
