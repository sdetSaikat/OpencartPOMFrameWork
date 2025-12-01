package com.qa.opencart.utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
	private JavascriptExecutor js;
	private WebDriver driver;
	
	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
		this.js=(JavascriptExecutor)this.driver;
		
	}
	
	public String getTitleByJS() {
		return js.executeScript("return document.title;").toString();
	}
	
	public String getURLByJS() {
		return js.executeScript("return document.URL;").toString();
	}
	
	//***********Page Navigation By JS*******************
	
	public void refreshPageByJs() {
		js.executeScript("history.go(0)");
	}
	
	public void navigateToBackPage() {
		js.executeScript("history.go(-1)");
	}
	
	
	public void navigateToForwardPage() {
		js.executeScript("history.go(1)");
	}
	
	
	/**
	 * The goal is to use the utilities while giving any demo
	 * after each page landing these alerts will come in the page 
	 * and disappeared after some  time.
	 * @param msg
	 */
	public void generateJsAlert(String msg) {
		js.executeScript("alert('"+msg+"')");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.switchTo().alert().dismiss();
	}
	
	
	/**
	 * To get the inner text of the entire page at once
	 * 
	 */
	
	public String getPageInnerText() {
		return js.executeScript("return document.documentElement.innerText").toString();
	}
	
	
	
	//******************* Scroll options by Js ***********************//
	
	public void partialScrollDownByJs(String height) {
		
		js.executeScript("window.scroolTo(0,'"+height+"')");
	}
	
	
	public void scrollDownByJs(String height) {
		
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}
	
	public void scrollUpByJs(String height) {
		
		js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
	}
	
	/**
	 * This funtion is for scroll to a particular element
	 * @param height
	 */
	public void scrolltoElementByJS(WebElement element) {
		
		js.executeScript("arguments[0].scrollIntoView(true)",element);
	}
	
	
	
	public void drawBorderOnElementByJS(WebElement element) {
		
		js.executeScript("arguments[0].style.border='3px solid red'",element);
	}
	
	
	
	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");//blue
		for (int i = 0; i < 5; i++) {
			changeColor("rgb(0,200,0)", element);// green
			changeColor(bgcolor, element);// blue
		}
	}

	
	
	
	private void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);//GBGBGBGBGBGB

		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}
	
	
	
	
	public void zoomChromeEdgeSaffari(String zoomPercentage) {
		String zoom = "document.body.style.zoom = '"+zoomPercentage+"%'";
		js.executeScript(zoom);
	}
	
	
	public void clickElementByJS(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}
	
	public void sendKeysUsingWithIdByJS(String id, String value) {
		js.executeScript("document.getElementById('" + id + "').value='" + value + "'");
						  //document.getElementById('input-email').value ='tom@gmail.com'
	}
}
