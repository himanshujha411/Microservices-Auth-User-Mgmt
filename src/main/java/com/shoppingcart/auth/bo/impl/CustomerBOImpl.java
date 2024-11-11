package com.shoppingcart.auth.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.auth.bo.CustomerBO;
import com.shoppingcart.auth.dao.CustomerDAO;
import com.shoppingcart.auth.vo.Customer;

@Service
public class CustomerBOImpl implements CustomerBO{

	@Autowired
	CustomerDAO customerDAO;
	
	@Override
	public int insertCustomer(Customer customer, int billAddId, int shipAddId) {
		//return 1;
		return customerDAO.insertCustomer(customer, billAddId, shipAddId);
	}
	
	@Override
	public Customer findCustomerByUserId(int userID) {
		return customerDAO.findCustomerByUserId(userID);
	}

}
