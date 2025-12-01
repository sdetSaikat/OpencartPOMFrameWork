package com.qa.opencart.constant;

import java.util.Arrays;
import java.util.List;

public class AppConstant {
	
	public  static final int SHORT_TIMEOUT_TIME =5;
	public  static final int MEDIUM_TIMEOUT_TIME =10;
	public  static final int MAXXIMUM_TIMEOUT_TIME =15;
	
	public  static final String EXPECTED_LOGINPAGE_TITLE ="Account Login";
	public  static final String EXPECTED_LOGINPAGE_FRACTION_URL ="route=account/login";
	public  static final String EXPECTED_HOMEPAGE_TITLE ="My Account";
	public  static final String EXPECTED_HOMEPAGE_FRACTION_URL ="route=account/account";
	
	public  static final String SUCCESSFULL_USER_REGISTRATION_FRACTION_URL ="route=account/success";
	
	
	/**
	 * either we can use Arrays.asList() or List.of() 
	 * method to create a List object out of any sequence
	 * 
	 */
	public  static List<String> expectedHeaderlist =Arrays.asList("My Account",
															"My Orders",
															"My Affiliate Account",
															"Newsletter");
	
//	public  static List<String> expectedHeaderlist = List.of("My Account","My Orders","My Affiliate Account","Newsletter");
	
	public  static final String REGISTRATION_SUCCESS_MESSAGE ="Your Account Has Been Created!";
	
	
	

}
