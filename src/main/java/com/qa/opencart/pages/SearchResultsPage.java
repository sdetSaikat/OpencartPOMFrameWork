package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utilities.ElementUtil;

import io.qameta.allure.Step;

import static com.qa.opencart.constant.AppConstant.*;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsPage {
	
	private WebDriver driver;
	private ElementUtil eleutil;
	
	
	private final By searchResults = By.cssSelector("div.product-thumb");
	private final By productsName = By.xpath("//div[@class = 'product-thumb']//a[text()]");
	
	
	
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}
	
	
	@Step("gentting the available product count..")
	public int getsearchResultsCount() {
		
		return eleutil.waitForAllElementsVisible(searchResults, SHORT_TIMEOUT_TIME).size();
		
		
	}
	
	public List<String> getProductNames() {
		
//		 List<WebElement> producteleList = eleutil.waitForAllElementsVisible(productsName, SHORT_TIMEOUT_TIME);
//		 List<String> productList= new ArrayList<String>();
//		 
//		 for(WebElement e :producteleList) {
//			 productList.add(e.getText());
//		 }
		
		return eleutil.getEleTextList(productsName);
		 
//		 return productList;
		 
		
	}
	
	@Step("selecting the product :{0}..")
	public ProductInfoPage doSelectProduct(String productName) {
		By productlocator =By.linkText(productName);
//		eleutil.doActionsClick(By.linkText(productName));
//		eleutil.waitForElementVisibility(By.linkText(productName), MAXXIMUM_TIMEOUT_TIME);
		eleutil.waitForElementPresence(productlocator, SHORT_TIMEOUT_TIME).click();
		
		eleutil.waitForElementVisibility(By.tagName("h1"), SHORT_TIMEOUT_TIME);
		return new ProductInfoPage(driver);
		
		
		
	}
	
	

}
