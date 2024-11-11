package com.shoppingcart.auth.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.shoppingcart.auth.common.LoadJPAFQueries;
import com.shoppingcart.auth.dao.SequencesDAO;
import com.shoppingcart.auth.dao.UserDAO;
import com.shoppingcart.auth.vo.User;

import jakarta.annotation.PostConstruct;

@Repository
public class UserDAOImpl extends JdbcDaoSupport implements UserDAO{

	@Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }
    
    @Autowired
    SequencesDAO sequencesDAO;
    
    private static final String userSequence = "users_seq";
    
	@Override
	public User findUserByUsername(String username) {
		 User user = getJdbcTemplate().queryForObject(LoadJPAFQueries.getQueryById("SELECT_USER_BY_USERNAME"), new Object[] { username },
	                BeanPropertyRowMapper.newInstance(User.class));
		return user;
	}
	
	@Override
	public boolean isUserEmailExist(String username) {
		//Integer idsequence = sequencesDAO.getNextSequence(ShipSequence);
		
		return getJdbcTemplate().queryForObject(LoadJPAFQueries.getQueryById("CHECK_UserEmail_Exists"),
                new Object[] {username}, Integer.class) > 0;
	}

	@Override
	public int insertUser(User user) {
		Integer userId = sequencesDAO.getNextSequence(userSequence);
		user.setUserId(userId);
		return getJdbcTemplate().update(LoadJPAFQueries.getQueryById("INSERT_USER"),
                new Object[] {
                		user.getUserId(), user.getEmailId(), user.getPassword()
		 });
                
	}
	
	
}
