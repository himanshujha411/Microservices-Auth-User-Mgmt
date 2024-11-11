package com.shoppingcart.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.auth.bo.UserBO;
import com.shoppingcart.auth.security.UserAuthenticationRequest;
import com.shoppingcart.auth.util.ServiceConstants;
import com.shoppingcart.auth.vo.User;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = ServiceConstants.REQ_MAP_URL_UserLoginBeanService)
public class UserLoginService {

	final static Logger LOG = LoggerFactory.getLogger(UserLoginService.class);
	
	@Autowired
	UserBO userBO;
	
	@RequestMapping(value = ServiceConstants.REQ_MAP_METHOD_URL_LOGIN, method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> login(@RequestBody UserAuthenticationRequest authenticationRequest)
	{
		Map<String,Object> statusMap = new HashMap<>();
		
		LOG.info("Username is : "+authenticationRequest.getUsername());
		
		String username = authenticationRequest.getUsername();
		try
		{
			User user = userBO.findUserByUsername(username);
			if(user!=null)
			{
				String password = authenticationRequest.getPassword();
				Boolean status =  (user.getPassword()).equals(password);
				
				if(status==true)
				{
					statusMap.put("User", user);
					statusMap.put("success","Login Successful");
					//request.getSession().setAttribute("token", username);
					//userBO.setUserSession(username);
					statusMap.put("token", username);
				}
				else {
					LOG.error("Password is wrong.");
					statusMap.put("error", "Username/Password is wrong.");
	            	return new ResponseEntity<>(statusMap,HttpStatus.UNAUTHORIZED);
				}
			}
			else {
				LOG.error("Username is wrong.");
				statusMap.put("error", "Username/Password is wrong.");
            	return new ResponseEntity<>(statusMap,HttpStatus.UNAUTHORIZED);
			}
		}
		catch(Exception e)
		{
			LOG.error("Username/Password is wrong.");
			e.printStackTrace();
			statusMap.clear();
			statusMap.put("error", "Username/Password is wrong.");
        	return new ResponseEntity<>(statusMap,HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<>(statusMap, HttpStatus.OK);
	}
}
