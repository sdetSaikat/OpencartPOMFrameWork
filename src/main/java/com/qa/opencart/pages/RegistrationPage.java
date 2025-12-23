package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utilities.ElementUtil;
import com.qa.opencart.utilities.StringUtil;

import static com.qa.opencart.constant.AppConstant.*;

public class RegistrationPage {
	
	private WebDriver driver;
	private ElementUtil eleutil;
	
	private final By userFirstName= By.id("input-firstname");
	private final By userLastName= By.id("input-lastname");
	private final By userEmail= By.id("input-email");
	private final By userTelephone= By.id("input-telephone");
	private final By userPassword= By.id("input-password");
	private final By userConfirmPassword= By.id("input-confirm");
	private final By agreeCheckbox= By.name("agree");
	private final By loginButton= By.xpath("//input[@value = 'Continue']");
	
	private final By subscribeYes= By.xpath("//input[@name='newsletter' and @value ='1']");
	private final By subscribeNo= By.xpath("//input[@name='newsletter' and @value ='0']");
	
	private final By logout= By.linkText("Logout");
	private final By register= By.linkText("Register");
	
	private final By successMsg= By.tagName("h1");
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}
	
	public boolean accountRegistration(String firstName,String lastName,String phoneNumber,
										String password,String subscribeChoice) 
	{
		eleutil.SendKeysWithWait(userFirstName, MEDIUM_TIMEOUT_TIME, firstName);
		eleutil.doSendKeys(userLastName, lastName);
		eleutil.doSendKeys(userEmail, StringUtil.getRandomEmail());
		eleutil.doSendKeys(userTelephone, phoneNumber);
		eleutil.doSendKeys(userPassword, password);
		eleutil.doSendKeys(userConfirmPassword, password);
		if(subscribeChoice.equalsIgnoreCase("yes")) {
			eleutil.doClick(subscribeYes);
		}
		else {
			eleutil.doClick(subscribeNo);
		}
		eleutil.doClick(agreeCheckbox);
		eleutil.doClick(loginButton);

		
		WebElement successele = eleutil.waitForElementVisibility(successMsg, MEDIUM_TIMEOUT_TIME);
		
		if (successele.getText()
				.contains(REGISTRATION_SUCCESS_MESSAGE)) {
//			eleutil.doClick(logout);
			eleutil.ClickWithWait(logout, MEDIUM_TIMEOUT_TIME);
			eleutil.ClickWithWait(register, MEDIUM_TIMEOUT_TIME);
//			eleutil.doClick(register);
//			try {
//				Thread.sleep(4000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			return true;
		}
		else {
			return false;
		}
		
		
		
	}

}
