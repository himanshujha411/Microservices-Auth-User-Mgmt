package com.shoppingcart.auth.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.shoppingcart.auth.common.LoadJPAFQueries;
import com.shoppingcart.auth.dao.CustomerDAO;
import com.shoppingcart.auth.dao.SequencesDAO;
import com.shoppingcart.auth.vo.Customer;

import jakarta.annotation.PostConstruct;

@Repository
public class CustomerDAOImpl extends JdbcDaoSupport implements CustomerDAO{

	@Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }
    
    @Autowired
    SequencesDAO sequencesDAO;
    
    private static final String customerSequence = "customer_seq";
    
    @Override
	public int insertCustomer(Customer customer, int billAddId, int shipAddId) {
		Integer customerId = sequencesDAO.getNextSequence(customerSequence);
		customer.setCustomerId(customerId);
		int userId = customer.getUser_id();
		return getJdbcTemplate().update(LoadJPAFQueries.getQueryById("INSERT_CUSTOMER"),
                new Object[] {
                		customer.getCustomerId(), customer.getCustomerPhone(), customer.getFirstName(),
                		customer.getLastName(), userId, billAddId, shipAddId             		
		 });
                
	}
    
    @Override
	public Customer findCustomerByUserId(int userID) {
    	Customer customer = getJdbcTemplate().queryForObject(LoadJPAFQueries.getQueryById("SELECT_CUSTOMER_BY_USERID"), new Object[] { userID },
	                BeanPropertyRowMapper.newInstance(Customer.class));
		return customer;
	}

}
