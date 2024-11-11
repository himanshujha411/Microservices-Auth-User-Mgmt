package com.shoppingcart.auth.common;

import java.util.Map;
public class LoadJPAFQueries {

    private static Map<String, String> queryMap = null;

    @SuppressWarnings("unchecked")
    synchronized static public Map<String, String> getQueriesMap() throws Exception {
        if (queryMap == null) {
            Object obj = LoadJPAFApplicationContext.getApplicationContext().getBean("queries");
            if(obj != null) {
                queryMap = (Map<String, String>) obj;
            } else {
                throw new Exception("Core Framework Database Queries file do not contain queries tag");
            }
        }
        return queryMap;
    }

    static public String getQueryById(String queryId) {
        String query = null;
        try {
            query = getQueriesMap().get(queryId);
        } catch (Exception e) {
        }
        return query;
    }
}
