package com.cheapticket.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	protected WebDriver driver;

	
	@Parameters("selecteddriver")
	@BeforeClass
	public void setBrowser(String selecteddriver) throws IOException {
		
		
		switch (selecteddriver) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "./libs/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", "./libs/geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		}
	}

	public Properties property(File file) throws IOException {
		Properties prop = new Properties();
		FileInputStream input = new FileInputStream(file);
		prop.load(input);
		return prop;
	}



}
