package com.shoppingcart.auth.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;

import com.shoppingcart.auth.bo.AuthoritiesBO;
import com.shoppingcart.auth.bo.CustomerBO;
import com.shoppingcart.auth.bo.UserBO;
import com.shoppingcart.auth.util.ServiceConstants;
import com.shoppingcart.auth.vo.BillingAddress;
import com.shoppingcart.auth.vo.Customer;
import com.shoppingcart.auth.vo.User;
import com.shoppingcart.auth.vo.UserRegistration;
import com.shoppingcart.auth.vo.ShippingAddress;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = ServiceConstants.REQ_MAP_URL_UserRegistrationBeanService)
public class UserRegistrationService {
	
	final static Logger LOG = LoggerFactory.getLogger(UserRegistrationService.class);
	
	@Autowired
	UserBO userBO;
	
	@Autowired
	CustomerBO customerBO;
	
	@Autowired
	AuthoritiesBO authoritiesBO;
	
	@Value("${service.delivery.delivery-base-url}")
    private String deliveryBaseURL;
	
	@Value("${service.orders.orders-base-url}")
    private String ordersBaseURL;
	
	@RequestMapping(value = ServiceConstants.REQ_MAP_METHOD_URL_SIGNUP, method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> signup(@RequestBody UserRegistration userRegistration,
    												HttpServletRequest request) throws AuthenticationException 
	{
		Map<String,Object> statusMap = new HashMap<>();
		User user = userRegistration.getUser();
		Customer customer = userRegistration.getCustomer();
		BillingAddress billingAddress = userRegistration.getBillAdd();
		ShippingAddress shippingAddress = userRegistration.getShipAdd();
		int billAddId=0;
		int shipAddId=0;
		int cartId = 0;
		
		//Validating Payload
		if(user!=null && customer!=null)
		{
			boolean isUserEmailExist = userBO.isUserEmailExist(user.getEmailId());
			
			//Check for existing user
			if(isUserEmailExist!=false)
			{
				statusMap.put("error","User Email already exist");
				LOG.info("User already exist");
				return new ResponseEntity<>(statusMap,HttpStatus.OK);
			}
		}
		else {
			statusMap.put("error","user info not correct");
			LOG.info("user/customer input not correct");
			return new ResponseEntity<>(statusMap,HttpStatus.BAD_REQUEST);
		}
		
		try {
			
		
		//User Insert
		int insertUserCount = userBO.insertUser(user);
		if(insertUserCount>0)
		{
			User savedUser = userBO.findUserByUsername(user.getEmailId());
			customer.setUser_id(savedUser.getUserId());
		}
		else {

			statusMap.put("error","User Registration Failed");
			LOG.info("insert user failed");
			return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Insert BillingAddress
		String url = deliveryBaseURL + "controller/UserDeliveryRegistration/insertbillingaddress";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<BillingAddress> requestEntity = new HttpEntity<>(billingAddress, headers);
		ResponseEntity<?> billId = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Integer.class);
		billAddId = Integer.parseInt(billId.getBody().toString());
		
		//Insert ShippingAddress
		String shippingUrl = deliveryBaseURL + "controller/UserDeliveryRegistration/insertshippingaddress";
		RestTemplate shipRestTemplate = new RestTemplate();
		HttpHeaders shipHeaders = new HttpHeaders();
		shipHeaders.setContentType(MediaType.APPLICATION_JSON);
		shipHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ShippingAddress> shipRequestEntity = new HttpEntity<>(shippingAddress);
		ResponseEntity<?> shipId = shipRestTemplate.exchange(shippingUrl, HttpMethod.POST, shipRequestEntity, Integer.class);
		shipAddId = Integer.parseInt(shipId.getBody().toString());
		
		if(billAddId<=0 || shipAddId<=0)
		{
			statusMap.put("error","User Registration Failed");
			LOG.info("insert billing/shipping address failed");
			return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Authorities Insert
		int insertAuthoritiesCount = authoritiesBO.insertAuthorities(user.getEmailId());
		if(insertAuthoritiesCount<=0)
		{
			statusMap.put("error","User Registration Failed");
			LOG.info("insert authorities failed");
			return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//Customer Insert
		int insertCustomerCount = customerBO.insertCustomer(customer, billAddId, shipAddId);
		if(insertCustomerCount<=0)
		{
			statusMap.put("error","User Registration Failed");
			LOG.info("insert customer failed");
			return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);				
		}
		Customer savedCustomer = customerBO.findCustomerByUserId(customer.getUser_id());
		
		String CartUrl = ordersBaseURL + "controller/UserRegistration/insertcart";
		RestTemplate cartRestTemplate = new RestTemplate();
		HttpHeaders cartHeaders = new HttpHeaders();
		cartHeaders.setContentType(MediaType.APPLICATION_JSON);
		cartHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Customer> cartRequestEntity = new HttpEntity<>(savedCustomer);
		ResponseEntity<?> cartCount = cartRestTemplate.exchange(CartUrl, HttpMethod.POST, cartRequestEntity, Integer.class);
		cartId = Integer.parseInt(cartCount.getBody().toString());
			
		if(cartId<=0)
		{
			statusMap.put("error","User Registration Failed");
			LOG.info("insert authorities failed");
			return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		catch(Exception e){
			e.printStackTrace();
		}
		statusMap.put("success","User Registration Successful");
		return new ResponseEntity<>(statusMap, HttpStatus.OK);
	}

}
