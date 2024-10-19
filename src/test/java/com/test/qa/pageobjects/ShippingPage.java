package com.test.qa.pageobjects;
import com.aventstack.extentreports.Status;
import com.test.qa.reportmanager.Report;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;


public class ShippingPage {

	WebDriver driver;

	public ShippingPage(WebDriver driver) {
		this.driver = driver;
	}
	public By locationBox = By.xpath("//input[@aria-controls='listbox--1']");
	public By locationSuggestion = By.xpath("//ul[@id='listbox--1']/li[1]");
	public By confirmLocation = By.xpath("(//span[contains(text(), 'Confirm location')])/parent::button");
	public By blockInput = By.id("block");
	public By paciInput = By.id("paciNumber");
	public By firstNameInput = By.id("firstName");
	public By lastNameInput = By.id("lastName");
	public By emailInput = By.id("email");
	public By primaryPhoneNumber = By.id("primaryPhoneNumber");
	public By deliveryInstruction = By.id("deliveryInstruction");
	public By confirmShippingAddress = By.xpath("//span[contains(text(), 'Confirm shipping address')]");
	public By editLocation=By.xpath("//*[@id='location']/..//following-sibling::div[1]");
	public By addNewAddress=By.xpath("//span[contains(text(), 'Add new address')]//parent::button");
	/**
	 * Login with valid credentials
	 * 
	 * @param userName
	 * @param password
	 * @throws Exception
	 */
	public void fillMapAndShipping() throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		Thread.sleep(3000);
		wait.until((ExpectedCondition<Boolean>) wd ->
				((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
//		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(editLocation));
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(editLocation)));
		driver.findElement(editLocation).click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(addNewAddress)));
		checkAddNewAddress();
		driver.findElement(locationBox).clear();
		driver.findElement(locationBox).sendKeys("Hawally,Kuwait");
		Thread.Sleep(100);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(locationSuggestion)));
		driver.findElement(locationSuggestion).click();
		Thread.Sleep(100);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(confirmLocation)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(confirmLocation));
		driver.findElement(confirmLocation).click();
//		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(blockInput)));
		driver.findElement(blockInput).sendKeys("1");
		Thread.sleep(20);
		driver.findElement(paciInput).clear();
		driver.findElement(paciInput).sendKeys("123");
		Thread.sleep(20);
		driver.findElement(firstNameInput).clear();
		driver.findElement(firstNameInput).sendKeys("UmaShankari");
		Thread.sleep(20);
		driver.findElement(lastNameInput).clear();
		driver.findElement(lastNameInput).sendKeys("A");
		Thread.sleep(20);
//		driver.findElement(emailInput).sendKeys("umaashankarii@gmail.com");
		Thread.sleep(20);
		driver.findElement(primaryPhoneNumber).clear();
		driver.findElement(primaryPhoneNumber).sendKeys("96598073413");
		Thread.sleep(20);
		driver.findElement(deliveryInstruction).clear();
		driver.findElement(deliveryInstruction).sendKeys("FirstStreet Near Kings coffee");
		Thread.sleep(20);

		// Create a Select object
		Select select = new Select(driver.findElement(By.id("modeOfCommunication")));
		// Select the value "Email" (or other values like "SMS", "WhatsApp")
		select.selectByVisibleText("Email");
		driver.findElement(confirmShippingAddress).click();
		wait.until(ExpectedConditions.urlToBe("https://www.xcite.com/checkout/delivery"));
		Report.log(Status.PASS, "Shipping/Billing address is added ");
	}
	public void checkAddNewAddress() throws Exception {
		try {

			// If OOS found, click decrease qty
			if (driver.findElement(addNewAddress).isDisplayed()) {
				driver.findElement(addNewAddress).click();
				System.out.println("addNewAddress found");
			}

		} catch (NoSuchElementException e) {
			// First element is not present, do nothing or log a message
			System.out.println("addNewAddress not found, so editing the previous address .");
		}
	}
}
