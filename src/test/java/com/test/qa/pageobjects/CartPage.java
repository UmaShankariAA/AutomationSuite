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


public class CartPage {

	WebDriver driver;
	public By increaseQtyBtn = By.xpath("//button[@aria-label='increase quantity']");
	public By qtyText = By.xpath("//*[@aria-label='decrease quantity']//following-sibling::span");
	public By Checkout = By.xpath("//span[contains(text(), 'Checkout')]");

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
	public void increaseQty() throws Exception {
		Thread.sleep(2000);
		String qtyString = driver.findElement(qtyText).getText();
		Integer qtyProduct = Integer.parseInt(qtyString);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(increaseQtyBtn)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", driver.findElement(increaseQtyBtn));
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(increaseQtyBtn)));
		qtyString = driver.findElement(qtyText).getText();
		Integer qtyProductNew = Integer.parseInt(qtyString);
		Assert.assertEquals((int) qtyProductNew, (int) qtyProduct+1, "The value is not incremented by 1.");
		// to be done : add a json object or map to be compared with pdp
		Report.log(Status.PASS, "Qty is increased");
		driver.findElement(Checkout).click();

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
