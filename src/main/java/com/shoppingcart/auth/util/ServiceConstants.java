package com.shoppingcart.auth.util;

public class ServiceConstants {
	
	public static final String SERVICE_URL_PREFIX = "/controller";
	public static final String REQ_MAP_URL_UserLoginBeanService = SERVICE_URL_PREFIX +"/UserLoginBeanService";
	public static final String REQ_MAP_URL_UserRegistrationBeanService = SERVICE_URL_PREFIX +"/UserRegistrationBeanService";
	public static final String REQ_MAP_URL_HomeService = SERVICE_URL_PREFIX +"/home";
	public static final String REQ_MAP_URL_RestService = SERVICE_URL_PREFIX +"/RestServices";
	
	public static final String REQ_MAP_METHOD_URL_LOGIN = "/login";
	public static final String REQ_MAP_METHOD_URL_SIGNUP = "/signup";
	public static final String REQ_MAP_METHOD_URL_UserDetails = "/userdetails";
	public static final String REQ_MAP_METHOD_URL_ContactUs = "/contactUs";
	
	public static final String REQ_MAP_METHOD_URL_findCustomerIdByUsername = "/findCustomerIdByUsername";
	public static final String REQ_MAP_METHOD_URL_findCustomerFromToken = "/findCustomerFromToken";

}
