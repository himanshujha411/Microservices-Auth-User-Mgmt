package com.shoppingcart.auth.dao;

import com.shoppingcart.auth.vo.Customer;

public interface CustomerDAO {

	//int insertCustomer(Customer customer);

	Customer findCustomerByUserId(int userID);

	int insertCustomer(Customer customer, int billAddId, int shipAddId);
}
