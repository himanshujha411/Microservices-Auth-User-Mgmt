package com.shoppingcart.auth.vo;

public class UserRegistration {
	
	ShippingAddress shipAdd;
	BillingAddress billAdd;
	User user;
	Customer customer;
	public ShippingAddress getShipAdd() {
		return shipAdd;
	}
	public void setShipAdd(ShippingAddress shipAdd) {
		this.shipAdd = shipAdd;
	}
	public BillingAddress getBillAdd() {
		return billAdd;
	}
	public void setBillAdd(BillingAddress billAdd) {
		this.billAdd = billAdd;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	

}
