package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constant.AppConstant;
import com.qa.opencart.utilities.ElementUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eleutil;
	
	
	private final By productImages = By.cssSelector("div#content img");
	private final By productAvailability = By.xpath("//div[@id='content']//li[contains(text(),'Availability')]");
	private final By productMetaDetails = By.xpath("(//div[@id ='content']//ul[@class = 'list-unstyled'])[1]/li");
	private final By productPricingDetails = By.xpath("(//div[@id ='content']//ul[@class = 'list-unstyled'])[2]/li");
	private  Map<String,String> productMap;
	
	
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}
	
	public int getProductImageCount() {
		
		
		int imageCount =eleutil.waitForAllElementsVisible(productImages, AppConstant.SHORT_TIMEOUT_TIME).size();
		System.out.println("Total number of image available is :-"+imageCount);
		return imageCount;
	}
	
	
	public String getProductAvailabilityStatus() {
		
		return eleutil.waitForElementVisibility(productAvailability,AppConstant.SHORT_TIMEOUT_TIME).getText();
		
		
	}
	
	
	
	
	public Map<String,String> getProductMetaDetails() {
		productMap = new HashMap<String, String>();
		productMap.put("imageCount", String.valueOf(5));
		productMap.put("productHeader", "MacBook");
		getProductMetaData();
		getProductPriceData();
		
		System.out.println("The product meta details are :"+ productMap);
		return productMap;
		
		
	}
	
	private void getProductMetaData() {
		List<WebElement> productDetails = eleutil.waitForAllElementsVisible(productMetaDetails, 
																				AppConstant.SHORT_TIMEOUT_TIME);
		
		for(WebElement e :productDetails) {
			
			String[] detailsarray = e.getText().split(":");
			productMap.put(detailsarray[0].trim(),detailsarray[1].trim());
		}
		
	}
	
	private void getProductPriceData() {
		List<WebElement> productPriceDetails = eleutil.waitForAllElementsVisible(productPricingDetails, 
																				AppConstant.SHORT_TIMEOUT_TIME);
		String actualPrice =productPriceDetails.get(0).getText();
		String priceWithoutTax = productPriceDetails.get(1).getText().split(":")[1].trim();
		
		productMap.put("ActualPrice", actualPrice);
		productMap.put("WithoutTaxPrice", priceWithoutTax);
		
		
	}
}
