package com.test.qa.pageobjects;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.Status;
import com.test.qa.reportmanager.Report;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import java.util.List;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import com.test.qa.driver.*;

public class CartPage {

	WebDriver driver;
	public By increaseQtyBtn = By.xpath("//button[@aria-label='increase quantity']");
	public By qtyText = By.xpath("//*[@aria-label='decrease quantity']//following-sibling::span");
//	public By Checkout = By.xpath("//span[contains(text(), 'Checkout')]//parent::button");
    public By Checkout = By.xpath("(//*[@class='button primaryOnLight '])");
	public By OOS = By.xpath("//span[contains(text(), 'item(s) is insufficient in stock')]");
	public By decreaseQtyBtn = By.xpath("//button[@aria-label='decrease quantity']");
	public By productName = By.xpath("//*[@class='flex flex-col gap-4']/div/a/h5");

	public CartPage(WebDriver driver) {
		this.driver = driver;
	}
	/**
	 * Login with valid credentials
	 * 
	 * @param userName
	 * @param password
	 * @throws Exception
	 */
	public void increaseQty(Map<String, String> productDetails) throws Exception {
		Thread.sleep(2000);
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(productName)));
		String productNameFromSearch = productDetails.get("productName");
		String productNameCartPage = driver.findElement(productName).getText();
		Report.log(Status.PASS, "Product Name in cart is"+productNameCartPage);
		Assert.assertEquals(productNameFromSearch, productNameCartPage, "Product name does not match between search and cart");
		String qtyString = driver.findElement(qtyText).getText();
		Integer qtyProduct = Integer.parseInt(qtyString);
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(increaseQtyBtn)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", driver.findElement(increaseQtyBtn));
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(increaseQtyBtn)));
		qtyString = driver.findElement(qtyText).getText();
		Integer qtyProductNew = Integer.parseInt(qtyString);
		Assert.assertEquals((int) qtyProductNew, (int) qtyProduct+1, "The value is not incremented by 1.");
		// to be done : add a json object or map to be compared with pdp
		Report.log(Status.PASS, "Qty is increased");
//		checkOOS();
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(Checkout)));
		driver.findElement(Checkout).click();

	}
	public void checkOOS() throws Exception {
		try {

			// If OOS found, click decrease qty
			if (driver.findElement(OOS).isDisplayed()) {
				driver.findElement(decreaseQtyBtn).click();
				System.out.println("OOS element found, so decreasing the item qunatity to proceed");
			}

		} catch (NoSuchElementException e) {
			// First element is not present, do nothing or log a message
			System.out.println("OOS element not found, so qunatity remains increased .");
		}
	}
	public void emptyCart() throws Exception {

		// Find all elements matching the given XPath
		List<WebElement> removeButtons = driver.findElements(By.xpath("//*[@class='button textLink ']//span[contains(text(), 'Remove')]"));
		// Iterate over the list and click each "Remove" button
		for (WebElement removeButton : removeButtons) {
			removeButton.click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("(//*[@class='button textLink ']//span[contains(text(), 'Remove')])[1]")).click();
			Thread.sleep(1000);
		}
//		Thread.sleep(2000);
//		//Remove OOS items
//		List<WebElement> removeOOS = driver.findElements(By.xpath("//*[@data-testid='remove-item-btn']"));
//
//		for (WebElement removeOO : removeOOS) {
//			removeOO.click();
//			Thread.sleep(2000);
//
//		}
	}
}
