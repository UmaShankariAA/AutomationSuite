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
import com.test.qa.reportmanager.Report;
import com.aventstack.extentreports.Status;





public class PDP {

	WebDriver driver;

	public By addToCartBtn = By.xpath("//button/span[contains(text(), 'Add to cart')]");
	public By addedToCart = By.xpath("//span[contains(text(), 'Added to cart')]");
	public By viewCartButton = By.xpath("//button[contains(text(), 'View cart')]");
	public PDP(WebDriver driver) {
		this.driver = driver;
	}
	public By productNameCartELement= By.xpath("//*[@class='font-body rtl:font-rtl typography-default mb-5 sm:typography-h2']");


	public void addToCart(Map<String, String> productDetails) throws Exception {
		Thread.sleep(2000);
		String pdpProductName=driver.findElement(productNameCartELement).getText();
		String productNameFromSearch = productDetails.get("productName");
		Report.log(Status.PASS, "Product Name in pdp page is"+pdpProductName);
		// Assert to verify the product names match
		Assert.assertEquals(pdpProductName, productNameFromSearch, "Product name does not match between search and pdp");
		WebElement addToCartElement=driver.findElement(addToCartBtn);
		scrollToElementAndClick(driver,addToCartElement);
		driver.findElement(addedToCart).click();
		Report.log(Status.PASS, "Item is added to the cart");
		driver.findElement(viewCartButton).click();
	}

	public void scrollToElementAndClick(WebDriver driver,WebElement element) {
		try {

			// Scroll to the element using JavascriptExecutor
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

			// Click the element after scrolling
			element.click();

		} catch (StaleElementReferenceException e) {
			// Handle stale element by re-locating and retrying
			// Scroll again just in case
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

			// Click the element
			element.click();
		}
	}
}
