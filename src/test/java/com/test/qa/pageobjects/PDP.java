package com.test.qa.pageobjects;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.Status;
import com.test.qa.reportmanager.Report;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;



public class PDP {

	WebDriver driver;

	public By addToCart = By.xpath("//button/span[contains(text(), 'Add to cart')]");
	public By addedToCart = By.xpath("//span[contains(text(), 'Added to cart')]");
	public By viewCartButton = By.xpath("//button[contains(text(), 'View cart')]");
	public PDP(WebDriver driver) {
		this.driver = driver;
	}
	public By productNameCartELement= By.xpath("//*[@class='font-body rtl:font-rtl typography-default mb-5 sm:typography-h2']");

	/**
	 * Login with valid credentials
	 * 
	 * @param userName
	 * @param password
	 * @throws Exception
	 */
	public void addToCart(Map<String, String> productDetails) throws Exception {
//		driver.get(BASE_URL);
//		Report.log(Status.PASS, "Navigated to the home page");
		Thread.sleep(2000);
		// to be done : add a json object or map to be compared with pdp
//		driver.findElement(addToCart).click();
		String pdpProductName=driver.findElement(productNameCartELement).getText();
		String productNameFromSearch = productDetails.get("productName");

		// Use TestNG or JUnit's assert to verify the product names match
		Assert.assertEquals(pdpProductName, productNameFromSearch, "Product name does not match between search and pdp");
		scrollToElementAndClick(driver);

		driver.findElement(addedToCart).click();
		Report.log(Status.PASS, "Item is added to the cart");
		driver.findElement(viewCartButton).click();


	}

	public void scrollToElementAndClick(WebDriver driver) {
		try {
			// Re-locate the element to avoid StaleElementReferenceException
			WebElement element = driver.findElement(By.xpath("//button/span[contains(text(), 'Add to cart')]"));

			// Scroll to the element using JavascriptExecutor
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

			// Click the element after scrolling
			element.click();

		} catch (StaleElementReferenceException e) {
			// Handle stale element by re-locating and retrying
			WebElement element = driver.findElement(By.xpath("//button/span[contains(text(), 'Add to cart')]"));

			// Scroll again just in case
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

			// Click the element
			element.click();
		}
	}
}
