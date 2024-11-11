package com.shoppingcart.auth.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.shoppingcart.auth.common.LoadJPAFQueries;
import com.shoppingcart.auth.dao.QueriesDAO;
import com.shoppingcart.auth.dao.SequencesDAO;
import com.shoppingcart.auth.vo.Queries;

import jakarta.annotation.PostConstruct;

@Repository
public class QueriesDAOImpl extends JdbcDaoSupport implements QueriesDAO{


	@Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }
    
    @Autowired
    SequencesDAO sequencesDAO;
    
    private static final String querySequence = "query_seq";
    
    @Override
    public int insertQuery(Queries query)
    {
    	Integer queryId = sequencesDAO.getNextSequence(querySequence);
    	query.setId(queryId);;
		return getJdbcTemplate().update(LoadJPAFQueries.getQueryById("Insert_Query"),
                new Object[] {
                		queryId, query.getEmail(), query.getMessage(), query.getSubject()
                });
    	
    }
}
