package com.shoppingcart.auth.dao;

import com.shoppingcart.auth.vo.User;

public interface UserDAO {

	User findUserByUsername(String username);

	boolean isUserEmailExist(String username);

	int insertUser(User user);
	
	
}
