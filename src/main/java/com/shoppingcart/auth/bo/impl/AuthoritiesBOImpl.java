package com.shoppingcart.auth.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.auth.bo.AuthoritiesBO;
import com.shoppingcart.auth.dao.AuthoritiesDAO;

@Service
public class AuthoritiesBOImpl implements AuthoritiesBO{

	@Autowired
	AuthoritiesDAO authoritiesDAO;
	
	@Override
	public int insertAuthorities(String emailId) {
		//return 1;
		return authoritiesDAO.insertAuthorities(emailId);
	}
}
