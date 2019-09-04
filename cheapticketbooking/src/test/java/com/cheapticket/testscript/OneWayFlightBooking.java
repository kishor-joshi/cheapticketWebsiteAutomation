package com.cheapticket.testscript;
import com.cheapticket.base.*;
import com.cheapticket.helper.HelperClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;


public class OneWayFlightBooking extends BaseClass{

	Properties prop = new Properties();

	@Test(priority=1)
	public void openURL() throws IOException
	{
		FileInputStream input = new FileInputStream(".\\src\\test\\resources\\propators\\propators.properties");

		prop.load(input);
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	@Test(priority=2,dataProvider="SearchProvider",dataProviderClass=HelperClass.class)
	public void bookTicket(String[] arr) throws InterruptedException
	{
		//driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));
		driver.findElement(By.xpath(prop.getProperty("domestic"))).click();
		driver.findElement(By.xpath(prop.getProperty("oneway"))).click();
		WebElement from = waitAndGetElement(30, driver,"(//input[@class='search'])[1]");
		from.sendKeys(arr[0]);
		Thread.sleep(1000);
		WebElement to = waitAndGetElement(30, driver,"(//input[@class='search'])[2]");
		to.sendKeys(arr[1]);
		Thread.sleep(1000);
		driver.findElement(By.xpath(prop.getProperty("depart"))).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(prop.getProperty("departdate"))).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(prop.getProperty("class"))).click();
		driver.findElement(By.xpath(prop.getProperty("submit"))).click();
		Thread.sleep(3000);
		WebElement book=waitAndGetElement(30,driver,"(//button[@class='ui button large orange'])[1]");
        book.click();
        Thread.sleep(1000);
		driver.findElement(By.xpath(prop.getProperty("email"))).sendKeys(arr[2]);
		String phNum=arr[3].replace(".", "").replace("E9", "");
		driver.findElement(By.xpath(prop.getProperty("mobile"))).sendKeys(phNum);
		driver.findElement(By.xpath(prop.getProperty("continue"))).click();
	    driver.findElement(By.xpath(prop.getProperty("dropdown1"))).click();
	    driver.findElement(By.xpath(prop.getProperty("Mr"))).click();
		driver.findElement(By.xpath(prop.getProperty("firstname1"))).sendKeys(arr[4]);
		driver.findElement(By.xpath(prop.getProperty("lastname1"))).sendKeys(arr[5]);
		driver.findElement(By.xpath(prop.getProperty("submitlast"))).click();
		  
		  
	}
	public WebElement waitAndGetElement(long seconds,WebDriver driver,final String xpath) {
		FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(seconds))
				.pollingEvery(Duration.ofSeconds(2))
				.ignoring(NoSuchElementException.class);
		
		WebElement element = fluentWait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver dvr) {
				return dvr.findElement(By.xpath(xpath));
			}
		});
		return element;
	}
	
	@AfterClass
	public void closeBrowser()
	{
		driver.quit();
	}
}

