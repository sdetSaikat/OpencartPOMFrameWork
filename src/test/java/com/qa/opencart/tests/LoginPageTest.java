package com.qa.opencart.tests;

import static com.qa.opencart.constant.AppConstant.EXPECTED_HOMEPAGE_TITLE;
import static com.qa.opencart.constant.AppConstant.EXPECTED_LOGINPAGE_FRACTION_URL;
import static com.qa.opencart.constant.AppConstant.EXPECTED_LOGINPAGE_TITLE;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.pages.AccountPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;



@Epic("Epic No:100-Design Test pages for opencart application")
@Feature("Feature No:50 -  Login page Feature")
@Story("Story No:10 - design test cases for login page")
public class LoginPageTest extends BaseTest{
	
	@Description("Login page title check")
	@Severity(SeverityLevel.MINOR)
	@Owner("Saikat maity")
	@Test(description ="checking the title of the login page")
	public void loginPageTiltleTest() {
		String actualTiltle = login.getLoginPageTitle();
		ChainTestListener.log("The title of the login page is "+actualTiltle);
		Assert.assertEquals(actualTiltle, EXPECTED_LOGINPAGE_TITLE);
		
	}
	
	
	@Description("Login page url check")
	@Severity(SeverityLevel.MINOR)
	@Owner("Saikat maity")
	@Test(description ="checking url of the login page")
	public void loginPageUrlTest() {
		String actloginPageUrl = login.getLoginPageUrl();
		ChainTestListener.log("The url of the login page is "+actloginPageUrl);
		Assert.assertTrue(actloginPageUrl.contains(EXPECTED_LOGINPAGE_FRACTION_URL));
	}
	
	
	@Description("Forgot password link existance check..")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Saikat maity")
	@Test(description ="checking the existance of the forgot password link")
	public void loginPageForgotLinkTest() {
		
		Assert.assertTrue(login.isForgotPwdLinkpresent());
	}
	
	
	@Description("Loging in with valid credential test")
	@Severity(SeverityLevel.BLOCKER)
	@Owner("Saikat maity")
	@Test(priority = Short.MAX_VALUE,description = "Checking the Login functionality")
	public void loinPageAccountTest() {
		
		AccountPage acc = login.doAccountLogIn(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(acc.accountPageTitle(), EXPECTED_HOMEPAGE_TITLE);
		
	}

}
