package com.shoppingcart.auth.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.auth.bo.UserBO;
import com.shoppingcart.auth.dao.UserDAO;
import com.shoppingcart.auth.vo.User;

@Service
public class UserBOImpl implements UserBO{

	@Autowired
	UserDAO userDAO;
	
	@Override
	public User findUserByUsername(String username) {
		return userDAO.findUserByUsername(username);
	}
	
	@Override
	public boolean isUserEmailExist(String username) {
		//return 1;
		return userDAO.isUserEmailExist(username);
	}
	
	@Override
	public int insertUser(User user) {
		//return 1;
		return userDAO.insertUser(user);
	}
	
	

}
