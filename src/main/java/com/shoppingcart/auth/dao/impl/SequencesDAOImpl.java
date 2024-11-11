package com.shoppingcart.auth.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.shoppingcart.auth.dao.SequencesDAO;

import jakarta.annotation.PostConstruct;

@Repository
public class SequencesDAOImpl extends JdbcDaoSupport implements SequencesDAO{
	
	@Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

	@Override
	public int getNextSequence(String sequenceName) {

		int id =  getJdbcTemplate().queryForObject("SELECT nextval('"+ sequenceName +"') ", null, Integer.class);
        return id;
	}

}
