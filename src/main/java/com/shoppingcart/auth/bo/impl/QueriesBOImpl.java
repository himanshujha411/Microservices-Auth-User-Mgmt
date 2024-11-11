package com.shoppingcart.auth.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.auth.bo.QueriesBO;
import com.shoppingcart.auth.dao.QueriesDAO;
import com.shoppingcart.auth.vo.Queries;

@Service
public class QueriesBOImpl implements QueriesBO{

	@Autowired
	QueriesDAO queriesDAO;
	
	@Override
	public int insertQuery(Queries query)
	{
		return queriesDAO.insertQuery(query);
	}
}
