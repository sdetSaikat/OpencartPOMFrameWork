package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constant.AppConstant;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.utilities.ExcelUtil;


public class UserRegistrationTest extends BaseTest {
	
	
	@BeforeClass
	public void registrationSetUp() {
		registration = login.doRegisterUserAccount();
	}
	
	
	@DataProvider(name = "excelUserInfo")
	public Object[][] excelUserdata() {
		return ExcelUtil.getTestData("registerData");
	}
	
	@DataProvider(name = "userInfo")
	public Object[][] userRistrationData() {
		
		return new Object[][] {
			{"Saikat","Maity","8147112334","Mahadev1234#","yes"},
			{"Subhashree","Raha","6290029521","Mahadev1234#","No"},
			{"Bibhas","Mondal","9641766046","Mahadev1234#","yes"},
		};
	}
	
	
	@Test(dataProvider ="excelUserInfo" )
	public void registerTest(String firstName,String lastName,String telephone,String password,String subscribe) {
	
		Assert.assertTrue(registration.accountRegistration(firstName, lastName, telephone, password, subscribe));
	}
	
	
	
//	
}
