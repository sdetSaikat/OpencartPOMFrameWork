package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utilities.ElementUtil;

import io.qameta.allure.Step;

import static com.qa.opencart.constant.AppConstant.*;

import java.util.ArrayList;
import java.util.List;

public class AccountPage {
	
	private WebDriver driver;
	private ElementUtil eleutil;
	
	private final By headers = By.xpath("//div[@id='content']//h2");
	private final By searchFileld = By.name("search");
	private final By searchIcon = By.xpath("//div[@id = 'search']//button[@type = 'button']");
	
	public AccountPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}
	
	@Step("getting the account page title")
	public String accountPageTitle() {
		
		return eleutil.waitForActualTitle(EXPECTED_HOMEPAGE_TITLE, SHORT_TIMEOUT_TIME);
	}
	
	@Step("getting the account page url")
	public String accountPageURL() {
		
		return eleutil.waitForUrlContains(EXPECTED_HOMEPAGE_FRACTION_URL, SHORT_TIMEOUT_TIME);
	}
	
	@Step("getting the list of headers available on the account page")
	public List<String> getAccPageHeaders() {
		List<WebElement> heardereleLists = eleutil.waitForAllElementsVisible(headers,SHORT_TIMEOUT_TIME);
		List<String> hearderLists = new ArrayList<String>();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(WebElement e : heardereleLists) {
			hearderLists.add(e.getText());
		}
		
		System.out.println("The account page headerlist is :-"+hearderLists);
		
		return hearderLists;
	}
	@Step("searching for the product:- {0}")
	public SearchResultsPage doSearchProduct(String productName) {
		
		System.out.println("Searching for "+productName +"....");
		eleutil.doSendKeys(searchFileld, productName);
		eleutil.doClick(searchIcon);
		
		
		
		return new SearchResultsPage(driver);
		
		
	}


}
