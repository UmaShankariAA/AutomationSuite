package com.test.qa.utilities;

import java.util.Properties;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.Status;
import com.test.qa.reportmanager.Report;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;


public class GeneralElementHandlers {
	WebDriver driver;
/*
	// Constructor to initialize WebDriver
	public GeneralElementHandlers(WebDriver driver) {
		this.driver = driver;
	}
*/
	/**
	 * Waits for the page to fully load by checking the document's ready state.
	 */
	public void waitForPageToLoad() {
		// Create an instance of WebDriverWait with a timeout of 30 seconds
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		// Use WebDriverWait to wait until the document's ready state is "complete"
		wait.until((ExpectedCondition<Boolean>) wd ->
				((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	}
}
