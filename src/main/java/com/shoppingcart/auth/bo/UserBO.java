package com.shoppingcart.auth.bo;

import com.shoppingcart.auth.vo.User;

public interface UserBO {

	User findUserByUsername(String username);
	boolean isUserEmailExist( String username);
	int insertUser(User user);
	
}
