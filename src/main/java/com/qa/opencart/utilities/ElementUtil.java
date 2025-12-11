package com.qa.opencart.utilities;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {

	private WebDriver driver;
	private Actions act;
	private JavaScriptUtil js;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
		js = new JavaScriptUtil(driver);

	}

	/**
	 * If the user maintain the locator in String format.This Function returns By
	 * locator.As at the findElement()/findElement() both requires By locator to
	 * find the element on the web page.
	 * 
	 * @param eleLocator
	 * @param lcatorType
	 * @return This function returns By locator out of String based locator.
	 */
	public static By getBy(String eleLocator, String lcatorType) {
		By locator = null;
		switch (lcatorType.toUpperCase()) {
		case "ID":
			locator = By.id(eleLocator);
			break;
		case "NAME":
			locator = By.name(eleLocator);
			break;
		case "CLASS":
			locator = By.className(eleLocator);
			break;
		case "XPATH":
			locator = By.xpath(eleLocator);
			break;
		case "CSS":
			locator = By.cssSelector(eleLocator);
			break;
		case "LINKTEXT":
			locator = By.linkText(eleLocator);
			break;
		case "PARTIALLINKTEXT":
			locator = By.partialLinkText(eleLocator);
			break;
		case "TAGNAME":
			locator = By.tagName(eleLocator);
			break;

		default:
			System.out.println("Please provie the correct locator type....");
			break;
		}
		return locator;

	}

	public WebElement getElement(String eleLocator, String lcatorType) {
		return driver.findElement(getBy(eleLocator, lcatorType));
	}

	@Step("entering the value {1} to the element with locator {0}")
	public void doSendKeys(By locator, String text) {
		nullcheck(text);
		getElement(locator).clear();
		getElement(locator).sendKeys(text);
	}

	public void doSendKeys(String eleLocator, String lcatorType, String text) {
		nullcheck(text);
		getElement(eleLocator, lcatorType).clear();
		getElement(eleLocator, lcatorType).sendKeys(text);
	}

	public void doSendKeys(By locator, CharSequence sequence) {
		getElement(locator).clear();
		getElement(locator).sendKeys(sequence);
	}

	@Step("clicking on the element with locator :{0}")
	public void doClick(By locator) {
		getElement(locator).click();

	}

	public void doClick(String eleLocator, String lcatorType) {
		getElement(eleLocator, lcatorType).click();

	}

	public String getElementText(By locator) {
		return getElement(locator).getText();

	}

	public String getElementAttribute(By locator, String attributeName) {
		nullcheck(attributeName);
		return getElement(locator).getDomAttribute(attributeName);

	}

	public String getElementPropertyValue(By locator, String propertyName) {
		nullcheck(propertyName);
		return getElement(locator).getDomProperty(propertyName);

	}

	@Step("checking the element with locator {0} is displayed or not..")
	public boolean elementIsDiplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("Element is not present with the loacator:---" + locator);
			return false;
		}
	}

	public boolean elementIsSelected(By locator) {
		try {
			return getElement(locator).isSelected();
		} catch (NoSuchElementException e) {
			System.out.println("Element is not present with the loacator:---" + locator);
			return false;
		}
	}

	public boolean elementIsEnabled(By locator) {
		try {
			return getElement(locator).isEnabled();
		} catch (NoSuchElementException e) {
			System.out.println("Element is not present with the loacator:---" + locator);
			return false;
		}
	}

	@Step("finding the element with locator : {0}")
	public WebElement getElement(By locator) {
		ChainTestListener.log("The locator used: " + locator.toString());
		WebElement element = driver.findElement(locator);
		highlightElement(element);
		return element;

	}

	private void highlightElement(WebElement element) {
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			js.flash(element);
		}
	}

	public WebElement getElementByWait(By locator, int timeout) {
		return waitForElementVisibility(locator, timeout);

	}

	private void nullcheck(String value) {
		if (value == null | value.length() == 0) {
			throw new RuntimeException("====Value can not be null===");
		}

	}

	// *****************************Find Elements Utilities *******************

	/**
	 * The aim is to get the text from each of of the element present inside the
	 * element list. and filter those text which is not empty.
	 * 
	 * @param locator
	 * @return the list of text
	 * 
	 *         This concept is web scraping using Selenium
	 */
	
	@Step("getting the list of element text for all the element present with locator:{0}..")
	public List<String> getEleTextList(By locator) {
		List<WebElement> elementsList = getElementsList(locator);
		List<String> textList = new ArrayList<String>();

		for (WebElement e : elementsList) {
			String eletext = e.getText();
			if (eletext.length() != 0) {
				textList.add(eletext);
			}
		}
		System.out.println(textList);
		return textList;
	}

	/**
	 * this function is to check if any element is displayed on the page using
	 * Findelements method. As incase of Findelements method return empty list
	 * though the element is not present on the dom.
	 * 
	 * @param eleLocator
	 * @param expectedcount
	 * @return Boolean
	 */
	public boolean isDipalyedWithFindElements(By eleLocator, int expectedcount) {
		List<WebElement> elementsList = getElementsList(eleLocator);
		if (elementsList.size() == expectedcount) {
			System.out.println("The element is displayed " + expectedcount + " times on the page");
			return true;

		} else {
			return false;
		}
	}

	/**
	 * This method is to select any specific field from any list of elemnets
	 * 
	 * @param locator
	 * @param ToSelect
	 */

	public void fieldSelection(By locator, String ToSelect) {
		List<WebElement> eleList = getElementsList(locator);
		for (WebElement e : eleList) {
			if (e.getText().length() != 0) {
				if (e.getText().contains(ToSelect)) {
					e.click();
					break;
				}
			}
		}
	}

	public int getEletListSize(By locator) {
		return getElementsList(locator).size();
	}

	public List<WebElement> getElementsList(By locator) {
		return driver.findElements(locator);
	}

	public List<WebElement> getElements(String eleLocator, String lcatorType) {
		return driver.findElements(getBy(eleLocator, lcatorType));
	}

	// ******************** Select based DropDown Handlings***********************//

	/**
	 * The aim is to select an option by the text visible to it.
	 * 
	 * @param locator
	 * @param optionText
	 * @return This function returns true/false based on selection.Aim is to
	 *         acknowlwdge a selection happed or not.
	 */

	public boolean selectionByVisibleText(By locator, String optionText) {
		WebElement dropDownEle = getElement(locator);
		Select select = new Select(dropDownEle);
		try {
			select.selectByVisibleText(optionText);
			return true;
		} catch (NoSuchElementException e) {
			System.out.println("The option is not available with the text:-" + optionText);
			return false;
		}
	}

	/**
	 * The aim is to select an option by the index of the option. Note:-index always
	 * starts from 0.
	 * 
	 * @param locator
	 * @param index
	 * @return This function returns true/false based on selection.Aim is to
	 *         acknowlwdge a selection happed or not.
	 */
	public boolean selectionByindex(By locator, int index) {
		WebElement dropDownEle = getElement(locator);
		Select select = new Select(dropDownEle);
		try {
			select.selectByIndex(index);
			return true;
		} catch (NoSuchElementException e) {
			System.out.println("The option is not available with the index:-" + index);
			return false;
		}
	}

	/**
	 * The aim is to select an option by the value of the value attribute of the
	 * option.
	 * 
	 * @param locator
	 * @param value
	 * @return This function returns true/false based on selection.Aim is to
	 *         acknowlwdge a selection happed or not.
	 */
	public boolean selectionByValue(By locator, String value) {
		WebElement dropDownEle = getElement(locator);
		Select select = new Select(dropDownEle);
		try {
			select.selectByValue(value);
			return true;
		} catch (NoSuchElementException e) {
			System.out.println("The option is not available with the value:-" + value);
			return false;
		}
	}

	/**
	 * The aim is to select the dropdown options from select object without using
	 * select based methods.
	 * 
	 * @param Droplocator
	 * @param toBeSelcted
	 * @return To acknoledge that the option is selected or not returning True/False
	 *         based on selection.
	 */
	public boolean dropDownWithOutMethods(By Droplocator, String toBeSelcted) {
		Select select = new Select(getElement(Droplocator));
		List<WebElement> optionslist = select.getOptions();
		for (WebElement e : optionslist) {
			if (e.getText().trim().equals(toBeSelcted)) {

				e.click();
				System.out.println(toBeSelcted + " option is selected from the dropdown ");
				return true;
			}
		}
		System.out.println(toBeSelcted + " option is not available in the dropdown ");
		return false;
	}

	/**
	 * The aim is to select the dropdown options without using select class and its
	 * methods.
	 * 
	 * @param optionsLocator
	 * @param toBeSelcted
	 * @return To acknoledge that the option is selected or not returning True/False
	 *         based on selection.
	 */
	public boolean dropDownWithOutSelectClasss(By optionsLocator, String toBeSelcted) {
		List<WebElement> optionlists = driver.findElements(optionsLocator);
		for (WebElement e : optionlists) {
			if (e.getText().equals(toBeSelcted)) {
				e.click();
				System.out.println(toBeSelcted + " option is selected from the dropdown ");
				return true;
			}
		}
		System.out.println(toBeSelcted + " option is not available in the dropdown ");
		return false;
	}

	/**
	 * The aim is to return the entire list of options available to a particular
	 * dropDown for further validation.
	 * 
	 * @param optionsLocator
	 * @return Returning a list of string having the test of each option element.
	 */
	public List<String> getOptionList(By optionsLocator) {
		Select select = new Select(getElement(optionsLocator));
		List<WebElement> optionElelists = select.getOptions();
		List<String> optionsList = new ArrayList<String>();
		for (WebElement e : optionElelists) {
			optionsList.add(e.getText().trim());
		}
		return optionsList;

	}

	// ********************Non select based drop Down
	// handling************************
	/**
	 * This is the generic method for Non select based dropdown Handling 1.This
	 * methods works for single option selection 2.This methods works for Multiple
	 * option selection 1.This methods works all option selection as well Note:For
	 * all Vallue we need to pass all or ALL or All
	 * 
	 * @param droplocator
	 * @param optionSelector
	 * @param choicevalue
	 * @return upon slection this method will return a boolean result. If the option
	 *         we trying to select is not present the this function will return
	 *         False
	 */
	public boolean nonselectDropDown(By droplocator, By optionSelector, String... choicevalue) {
		getElement(droplocator).click();
		List<WebElement> optionList = getElementsList(optionSelector);

		boolean flag = false;

		if (choicevalue[0].equalsIgnoreCase("all")) {
			for (WebElement e : optionList) {
				e.click();
			}
		} else {
			for (WebElement e : optionList) {
				String text = e.getText();
				for (String s : choicevalue) {
					if (text.trim().equals(s)) {
						e.click();
						flag = true;
						break;
					} else {
						flag = false;
					}
				}
			}
		}
		return flag;
	}

	// ************************Parent Menu Sub menu Handling Using Action
	// Class*******************

	public void parentMenuSubMenuAction(By ParentMenuLocator, By subMenuLocator) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(ParentMenuLocator)).perform();
		Thread.sleep(2000);
		getElement(subMenuLocator).click();

	}

	/**
	 * This Wraper function is designed for Big basket.It depends on apllication to
	 * apllication. So this is a application based utility.
	 * 
	 * @param shopBy
	 * @param electronics
	 * @param cameraAccesories
	 * @param instantCameras
	 * @throws InterruptedException
	 */
	public void fourLevelMenuHandling(By shopBy, By electronics, By cameraAccesories, By instantCameras)
			throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(shopBy)).click().perform();
		Thread.sleep(2000);
		act.moveToElement(getElement(electronics)).perform();
		Thread.sleep(2000);
		act.moveToElement(getElement(cameraAccesories)).perform();
		Thread.sleep(2000);
		act.moveToElement(getElement(instantCameras)).click().perform();

	}

	public void doActionsClick(By locator) {
		act.click(getElement(locator)).perform();
	}

	public void doActionsSendkeys(By locator, String value) {
		act.sendKeys(getElement(locator), value).perform();
	}

	/**
	 * This method is to send the each character of a string value one by one to a
	 * particular text field.user has the choice to give the pause time after
	 * entering each character to make it visible.
	 * 
	 * @param locator
	 * @param value
	 * @param pauseTime
	 */
	public void doSendkeysWithPause(By locator, String value, long pauseTime) {
		char valueArray[] = value.toCharArray();
		for (char c : valueArray) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(pauseTime).perform();
		}
	}

	// ********************* Wait utilities************************

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public List<WebElement> waitForAllElementsPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	@Step("Waiting for all the elements with locator: {0} to become visible..")
	public List<WebElement> waitForAllElementsVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		} catch (TimeoutException e) {

			return Collections.EMPTY_LIST;
		}
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	@Step("wait for the element with locator:{0} to become visible")
	public WebElement waitForElementVisibility(By locator, int timeout) {
		ChainTestListener.log("The locator Used: " + locator.toString());
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;
	}

	/**
	 * An expectation for checking that the title contains a case-sensitive
	 * substring
	 * 
	 * @param partialTitle
	 * @param timeout
	 * @return
	 */
	public String waitForTitleContains(String partialTitle, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.titleContains(partialTitle));
			return driver.getTitle();
		} catch (TimeoutException t) {
			System.out.println("The actual Title does not contain::-" + partialTitle);
			return null;
		}
	}

	/**
	 * An expectation for checking the title of a page.
	 * 
	 * @param ExpectedTitle
	 * @param timeout
	 * @return
	 */
	@Step("Waiting for the title {0}")
	public String waitForActualTitle(String ExpectedTitle, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.titleIs(ExpectedTitle));
			return driver.getTitle();
		} catch (TimeoutException t) {
			System.out.println(ExpectedTitle + ": is not the Actual title ");
			return null;
		}
	}

	// Alert Handling with wait

	public Alert waitForAlert(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public void acceptAlert(int timeout) {
		waitForAlert(timeout).accept();

	}

	public void dismissAlert(int timeout) {
		waitForAlert(timeout).dismiss();

	}

	public String getTextOfAlert(int timeout) {
		return waitForAlert(timeout).getText();

	}

	public void sendTextToAlert(String text, int timeout) {
		waitForAlert(timeout).sendKeys(text);

	}

	// wait for the URl
	@Step("waiting for the url containing:: {0}")
	public String waitForUrlContains(String partialUrl, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.urlContains(partialUrl));
			return driver.getCurrentUrl();
		} catch (TimeoutException t) {
			System.out.println("The actual URL does not contain::-" + partialUrl);
			return null;
		}
	}

	public String waitForActualUrl(String ExpectedUrl, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.urlToBe(ExpectedUrl));
			return driver.getCurrentUrl();
		} catch (TimeoutException t) {
			System.out.println(ExpectedUrl + ": is not the actual  Url");
			return null;
		}
	}

	// wait for frame

	public void waitForFrameandSwitchToIt(By frameLocator, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));

	}

	public void waitForFrameandSwitchToIt(String NameOrId, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(NameOrId));

	}

	public void waitForFrameandSwitchToIt(int frameindex, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameindex));

	}

	public void waitForFrameandSwitchToIt(WebElement frameElement, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));

	}

	// wait for windows:

	public boolean waitForWindow(int expectedwindows, int timout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedwindows));

		} catch (TimeoutException e) {
			System.out.println("Expected number of window is not correct ");
			return false;
		}
	}

	public void clickWhenReady(By locator, int timout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		;

	}

	/**
	 * This mehods can be used for an expectation of wait for the page to become
	 * fully loaded at first With this we acn avoid many common exceptions
	 * 
	 * @param timeOut
	 * @return
	 */
	public boolean isPageLoaded(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"))
				.toString();
		return Boolean.parseBoolean(flag);// true
	}

	// wait for the element at first to appear then click or sendkeys

	public void ClickWithWait(By locator, int timeout) {
		waitForElementVisibility(locator, timeout).click();
	}

	public void SendKeysWithWait(By locator, int timeout, CharSequence... value) {
		getElement(locator).clear();
		waitForElementVisibility(locator, timeout).sendKeys(value);
	}

	// Get the element By FluentWait

	public WebElement visibilityOfWebElementByFluentWait(By locator, int pollingTime, int timeOut) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(ElementNotInteractableException.class).withMessage("=========Element not found=======");

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement presenceOfWebElementByFluentWait(By locator, int pollingTime, int timeOut) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(ElementNotInteractableException.class).withMessage("=========Element not found=======");

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

}
