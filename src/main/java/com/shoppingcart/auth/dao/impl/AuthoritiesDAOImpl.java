package com.shoppingcart.auth.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.shoppingcart.auth.common.LoadJPAFQueries;
import com.shoppingcart.auth.dao.AuthoritiesDAO;
import com.shoppingcart.auth.dao.SequencesDAO;

import jakarta.annotation.PostConstruct;

@Repository
public class AuthoritiesDAOImpl extends JdbcDaoSupport implements AuthoritiesDAO{

	@Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }
    
    @Autowired
    SequencesDAO sequencesDAO;
    
    private static final String authoritiesSequence = "authorities_seq";
    
    @Override
	public int insertAuthorities(String emailID) {
		Integer authoritiesId = sequencesDAO.getNextSequence(authoritiesSequence);
		String role = "ROLE_USER";
		return getJdbcTemplate().update(LoadJPAFQueries.getQueryById("INSERT_AUTHORITIES"),
                new Object[] {authoritiesId, role, emailID });
                
	}
}
