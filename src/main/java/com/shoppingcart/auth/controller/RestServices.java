package com.shoppingcart.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.auth.bo.CustomerBO;
import com.shoppingcart.auth.bo.UserBO;
import com.shoppingcart.auth.util.ServiceConstants;
import com.shoppingcart.auth.vo.Customer;
import com.shoppingcart.auth.vo.User;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = ServiceConstants.REQ_MAP_URL_RestService)
public class RestServices {

	final static Logger LOG = LoggerFactory.getLogger(RestServices.class);
	
	private static final String TOKEN_HEADER = "Authorization";
	
	@Autowired
	UserBO userBO;
	
	@Autowired
	CustomerBO customerBO;
	
	@RequestMapping(value = ServiceConstants.REQ_MAP_METHOD_URL_findCustomerIdByUsername, method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> findCustomerIdByUsername(HttpServletRequest request) throws AuthenticationException 
	{
		String token = request.getHeader(TOKEN_HEADER);
		User user = userBO.findUserByUsername(token);
		Customer customer = customerBO.findCustomerByUserId(user.getUserId());
		
		
		if(customer==null)
		{
			LOG.info("Customer is NULL");
			return new ResponseEntity<>(0,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		int customerId = customer.getCustomerId();
		
		return new ResponseEntity<>(customerId, HttpStatus.OK);
	}
	
	@RequestMapping(value = ServiceConstants.REQ_MAP_METHOD_URL_findCustomerFromToken, method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> findCustomerfromToken(HttpServletRequest request) throws AuthenticationException 
	{
		String token = request.getHeader(TOKEN_HEADER);
		User user = userBO.findUserByUsername(token);
		Customer customer = customerBO.findCustomerByUserId(user.getUserId());
		
		if(customer!=null)
		{
			return new ResponseEntity<>(customer, HttpStatus.OK);
		}
		LOG.info("Customer is NULL");
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
