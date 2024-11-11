package com.shoppingcart.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.auth.bo.CustomerBO;
import com.shoppingcart.auth.bo.QueriesBO;
import com.shoppingcart.auth.bo.UserBO;
import com.shoppingcart.auth.util.ServiceConstants;
import com.shoppingcart.auth.vo.Customer;
import com.shoppingcart.auth.vo.Queries;
import com.shoppingcart.auth.vo.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(value = ServiceConstants.REQ_MAP_URL_HomeService)
public class HomeService {
	
	final static Logger LOG = LoggerFactory.getLogger(HomeService.class);
	
	private static final String TOKEN_HEADER = "Authorization";
	
	@Autowired
	UserBO userBO;
	
	@Autowired
	CustomerBO customerBO;
	
	@Autowired
	QueriesBO queriesBO;
	
	@RequestMapping(value = ServiceConstants.REQ_MAP_METHOD_URL_UserDetails, method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userDetails(HttpServletRequest request) throws AuthenticationException 
	{
		Map<String,Object> statusMap = new HashMap<>();
		
		String token = request.getHeader(TOKEN_HEADER);
		User user = null;
		Customer customer = null;
		try {
			user = userBO.findUserByUsername(token);
			customer = customerBO.findCustomerByUserId(user.getUserId());
		}
		catch(Exception e) {}
		
		if(user!=null)
		{
			return new ResponseEntity<>(customer, HttpStatus.OK);
		}
		else {
			statusMap.put("error", "User not found!");
        	return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = ServiceConstants.REQ_MAP_METHOD_URL_ContactUs, method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> insertQuery(@RequestBody Queries query,
    												HttpServletRequest request) throws AuthenticationException 
	{
		Map<String,Object> statusMap = new HashMap<>();
		
		//LOG.info("Username is : "+authenticationRequest);
		
		String token = request.getHeader(TOKEN_HEADER);
		User user = null;
		try {
			user = userBO.findUserByUsername(token);
		}
		catch(Exception e) {}
		
		int insertQueryCount = 0;
		if(user!=null)
		{
			insertQueryCount = queriesBO.insertQuery(query);
			statusMap.put("success", "Thank you, Your Message is stored in our server and we will contact through corresponding Mail");
			
		}
		else {
			statusMap.put("error", "The incoming token has expired, please Login again");
        	return new ResponseEntity<>(statusMap,HttpStatus.UNAUTHORIZED);
		}
		
		if(insertQueryCount<=0)
		{
			statusMap.put("error","Send Query Failed");
			LOG.info("Query Update Failed");
			return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(statusMap, HttpStatus.OK);
	}
}
