package com.shoppingcart.auth.bo;

import com.shoppingcart.auth.vo.Customer;

public interface CustomerBO {

	//int insertCustomer(Customer customer);

	Customer findCustomerByUserId(int userID);

	int insertCustomer(Customer customer, int billAddId, int shipAddId);
}
