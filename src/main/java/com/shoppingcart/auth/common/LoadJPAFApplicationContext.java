package com.shoppingcart.auth.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LoadJPAFApplicationContext {

    private static ApplicationContext applicationContext = null;
    private static final String DATABASE_QUERY = "database-queries.xml";

    synchronized static public ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext(DATABASE_QUERY);
            //System.out.println("####======= Loading queries from jpaf-core-dao : "+DATABASE_QUERY);
        }
        return applicationContext;
    }
}