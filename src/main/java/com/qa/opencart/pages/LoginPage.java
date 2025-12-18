package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utilities.ElementUtil;

import io.qameta.allure.Step;

import static com.qa.opencart.constant.AppConstant.*;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleutil;
	
	
	private final By username = By.id("input-email");
	private final By password = By.id("input-password");
	private final By loginbtn = By.xpath("//input[@value = 'Login']");
	private final By forgotPasswordLink = By.linkText("Forgotten Password");
	private final By registerButton = By.xpath("//aside[@id='column-right']//a[text() = 'Register']");
	private final By logoutLinkInMyAccount = By.xpath("//aside[@id='column-right']//a[text()='Logout']");
	
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}
	
	
	@Step("getting the login page title..")
	public String getLoginPageTitle() {
		String loginPagetitle =eleutil.waitForActualTitle(EXPECTED_LOGINPAGE_TITLE, SHORT_TIMEOUT_TIME);
		System.out.println("Login Page title is "+loginPagetitle);
		return loginPagetitle;
	}
	
	@Step("getting the login page url...")
	public String getLoginPageUrl() {
		String loginPageUrl =eleutil.waitForUrlContains(EXPECTED_LOGINPAGE_FRACTION_URL, SHORT_TIMEOUT_TIME);
		System.out.println("Login Page url is "+loginPageUrl);
		return loginPageUrl;
	}
	
	@Step("getting the information about forgot passowd link existance..")
	public boolean isForgotPwdLinkpresent() {
		return eleutil.elementIsDiplayed(forgotPasswordLink);
	}
	
	@Step("Loging in with credential as userName={0} and password={1}..")
	public AccountPage doAccountLogIn(String email,String pwd) {
		
		System.out.println("The username and password is :-" + email +"--"+pwd);
		
		eleutil.waitForElementVisibility(username, SHORT_TIMEOUT_TIME).sendKeys(email);
		eleutil.doSendKeys(password, pwd);
		eleutil.doClick(loginbtn);
	
		
		return new AccountPage(driver);
	
	}
	@Step("Navigating to the registration page..")
	public RegistrationPage doRegisterUserAccount() {
		eleutil.ClickWithWait(registerButton, SHORT_TIMEOUT_TIME);
		eleutil.waitForElementVisibility(logoutLinkInMyAccount, MEDIUM_TIMEOUT_TIME);
		return new RegistrationPage(driver);
		
	}
	
	
	

}
