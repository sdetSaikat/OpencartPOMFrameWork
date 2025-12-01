package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {
	private Properties prop;
	
	public OptionsManager(Properties prop) {
		this.prop = prop;
	}
	
	public  ChromeOptions getChomeOptions() {
		
		ChromeOptions co = new ChromeOptions();
		
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("---Running in headless mode----");
			co.addArguments("--headless");
		}
		
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("---Running in inconito mode----");
			co.addArguments("--incognito");
		}
		
		return co;
		
		
	}
	
	public  FirefoxOptions getFirefoxOptions() {
		
		FirefoxOptions fo = new FirefoxOptions();
		
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("---Running in headless mode----");
			fo.addArguments("--headless");
		}
		
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("---Running in private mode----");
			fo.addArguments("-private");
		}
		
		return fo;
		
		
	}
	
	public  EdgeOptions getEdgeOptions() {
		
		EdgeOptions eo = new EdgeOptions();
		
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("---Running in headless mode----");
			eo.addArguments("--headless");
		}
		
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("---Running in private mode----");
			eo.addArguments("--inprivate");
		}
		
		return eo;
		
		
	}

}
