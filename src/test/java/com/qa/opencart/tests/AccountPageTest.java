package com.qa.opencart.tests;

import static com.qa.opencart.constant.AppConstant.EXPECTED_HOMEPAGE_FRACTION_URL;
import static com.qa.opencart.constant.AppConstant.EXPECTED_HOMEPAGE_TITLE;
import static com.qa.opencart.constant.AppConstant.expectedHeaderlist;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Epic No:100-Design Test pages for opencart application")
@Feature("Feature No:51 -  Account page Feature")
@Story("Story No:15 - design test cases for Account page")
public class AccountPageTest extends BaseTest {
	
	
	
	
	@BeforeClass
	public void accountPageSetup() {
		accPage = login.doAccountLogIn(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	@Description("Account page title check")
	@Severity(SeverityLevel.MINOR)
	@Owner("Saikat Maity")
	
	@Test(description ="checking the title of the account page")
	public void accountPageTitleTest() {
		
		Assert.assertTrue(accPage.accountPageTitle().contains(EXPECTED_HOMEPAGE_TITLE));
	}
	
	@Description("Account page url check")
	@Severity(SeverityLevel.MINOR)
	@Owner("Saikat Maity")
	
	@Test(description ="checking the url of the account page")
	public void accountPageUrlTest() {
		
		Assert.assertTrue(accPage.accountPageURL().contains(EXPECTED_HOMEPAGE_FRACTION_URL));
	}
	
	
	@Description("Account page Headers check")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Saikat Maity")
	@Test(description ="checking the List of Headers available in the account page")
	public void accountPageHeaderTest() {
		
		Assert.assertEquals(accPage.getAccPageHeaders(), expectedHeaderlist);
	}


}
