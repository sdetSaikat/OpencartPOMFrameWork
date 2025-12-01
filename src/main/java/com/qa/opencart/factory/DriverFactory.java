package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exceptions.BrowserExceptions;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {
	
	
	WebDriver driver;
	Properties prop;
	public static String highlight;
	OptionsManager optionsManager;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	public static Logger log =LogManager.getLogger(DriverFactory.class);
	
	/**
	 * This method is used to initialize the driver based on the browser name
	 * @param browserName
	 * @return
	 */
	public WebDriver initializeDriver(Properties prop) {
		
		log.info("Property used:-"+prop);
		String browserName =prop.getProperty("browserName");
		log.info("Launching the " + browserName+ " browser...");
//		System.out.println("Launching the " + browserName+ " browser...");
		
		optionsManager = new OptionsManager(prop);
		highlight = prop.getProperty("highlight");
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(optionsManager.getChomeOptions()));
			break;
		case "firefox":
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
			
		case "safari":
			tlDriver.set(new SafariDriver());
			break;
			
		case "edge":
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;

		default:
			log.error("Please pass the right browser name");
//			System.out.println("Please pass the right browser name");
			throw new BrowserExceptions("====INVALID BROWSER======");
		}
		
		getDriver().get(prop.getProperty("url"));
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		return getDriver();
		
	}
	
	
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	
	public static File getScreenShotasFile() {
		File screenshotAsFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		return screenshotAsFile;
	}
	
	public static byte[] getScreenShotAsByte() {
		byte[] screenshotAsByte = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
		return screenshotAsByte;
	}
	
	
	public static String getScreenShotAsBase64() {
		String screenshotAsBase64 = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);
		return screenshotAsBase64;
	}
	
	/**
	 * This method is used to initialize the config properties
	 * @return
	 */
	
	//mvn clean install -D env =qa
	public Properties initProp() {
		
		prop = new Properties();
		String env = System.getProperty("env");
		FileInputStream ip =null;
		try {
		if(env == null) {
			log.warn("No specific environment is slected,hence executing the tests on QA environment");
//			System.out.println("No specific environment is slected,hence executing the tests on QA environment");
			ip=new FileInputStream("./src/test/resources/config/qa.config.properties");
		}
		else {
			log.info("--Running the tests on "+env +" environment---");
//			System.out.println("--Running the tests on "+env +" environment---");
			switch (env.toLowerCase().trim()) {
			case "prod":
				ip=new FileInputStream("./src/test/resources/config/config.properties");
				break;
			case "dev":
				ip=new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			case "stage":
				ip=new FileInputStream("./src/test/resources/config/stage.config.properties");
				break;
			case "uat":
				ip=new FileInputStream("./src/test/resources/config/uat.config.properties");
				break;
			case "qa":
				ip=new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;

			default:
				throw new FrameworkException("=====INVALID ENVIRONMENT====="+env);
			}
		}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

}
