package com.test.qa.pageobjects;
import com.aventstack.extentreports.Status;
import com.test.qa.reportmanager.Report;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.HashMap;
import java.util.Map;
import com.test.qa.driver.*;
import com.test.qa.driver.*;
import org.testng.Assert;


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
	public By mapMapUI=By.xpath("(//*[@class='gm-style-mtc'])[1]");
	public By mapSatelliteUI=By.xpath("(//*[@class='gm-style-mtc'])[2]");
	String checkBoxXPath="//li[@role='menuitemcheckbox' and @title='Show imagery with street names']";
	String checkBoxTerrainXPath="//li[@role='menuitemcheckbox' and @title='Show street map with terrain']";
	String mapContainerXPath = "//div[@class='gm-style']";

	public void fillMapAndShipping() throws Exception {

		Thread.sleep(3000);
		DriverManager.getWait(60).until((ExpectedCondition<Boolean>) wd ->
				((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(editLocation)));
		driver.findElement(editLocation).click();
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(addNewAddress)));
		checkAddNewAddress();
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(mapSatelliteUI)));
		driver.findElement(mapSatelliteUI).click();
		validateCheckBoxBehavior(checkBoxXPath,mapContainerXPath);
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(mapSatelliteUI)));
		driver.findElement(mapMapUI).click();
		validateCheckBoxBehavior(checkBoxTerrainXPath,mapContainerXPath);
		driver.findElement(locationBox).clear();
		driver.findElement(locationBox).sendKeys("Hawally,Kuwait");
		Thread.sleep(100);
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(locationSuggestion)));
		driver.findElement(locationSuggestion).click();
		Thread.sleep(100);
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(confirmLocation)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(confirmLocation));
		driver.findElement(confirmLocation).click();
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(blockInput)));
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
		DriverManager.getWait(60).until(ExpectedConditions.urlToBe("https://www.xcite.com/checkout/delivery"));
		Report.log(Status.PASS, "Shipping/Billing address is added ");
	}
	public void checkAddNewAddress() throws Exception {
		try {


			if (driver.findElement(addNewAddress).isDisplayed()) {
				driver.findElement(addNewAddress).click();
				System.out.println("addNewAddress found");
			}

		} catch (NoSuchElementException e) {
			// First element is not present, do nothing or log a message
			System.out.println("addNewAddress not found, so editing the previous address .");
		}
	}

	// Method to handle checkbox click and validation of label appearance
	public void validateCheckBoxBehavior(String checkBoxXPath, String mapContainerXPath) {
		// Locate the checkbox element
		WebElement checkBox = driver.findElement(By.xpath(checkBoxXPath));

		// Locate the map container (or label) that changes visibility
		WebElement mapContainer = driver.findElement(By.xpath(mapContainerXPath));

		// Click the checkbox to select it
		checkBox.click();

		// Wait and validate if the CSS visibility or display has changed
		validateVisibility(mapContainer, "after checking");

		// Click the checkbox again to unselect it
		checkBox.click();

		// Validate the CSS visibility or display change after unchecking
		validateVisibility(mapContainer, "after unchecking");
	}

	// Method to check visibility or display properties
	private void validateVisibility(WebElement element, String action) {
		String visibility = element.getCssValue("visibility");
		String display = element.getCssValue("display");

		if (visibility.equals("visible") && !display.equals("none")) {
			System.out.println("Map label appears " + action + " the checkbox.");
			Assert.assertTrue(visibility.equals("visible") && !display.equals("none"),
					"Expected map label to appear, but it didn't.");
		} else {

			System.out.println("Map label disappears " + action + " the checkbox.");
			Assert.assertFalse(visibility.equals("visible") && !display.equals("none"),
					"Expected map label to disappear, but it is still visible.");
		}
	}
}
