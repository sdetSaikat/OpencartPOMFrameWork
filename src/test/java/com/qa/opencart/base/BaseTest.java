package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.RegistrationPage;


//@Listeners(ChainTestListener.class) 
//or else directly in the test runner xml file

public class BaseTest {
	
	DriverFactory df;
	WebDriver driver;
	protected Properties prop;
	protected LoginPage login;
	protected AccountPage accPage;
	protected RegistrationPage registration;
	
	@Parameters("browser")
	@BeforeTest
	public void setUp(@Optional String browserName) {
		
		df = new DriverFactory();
		prop =df.initProp();
		if(browserName!=null) {
			prop.setProperty("browserName",browserName);
		}
		driver = df.initializeDriver(prop);
		login= new LoginPage(driver);
		
		
	}
	
	@AfterMethod
	public void attachScreenShots(ITestResult tResult) {
		if(!tResult.isSuccess()) {
		ChainTestListener.embed(DriverFactory.getScreenShotAsBase64(), "image/png");
		}
		
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
