package com.test.qa.pageobjects;
import java.util.UUID;
import org.openqa.selenium.*;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.Status;
import com.test.qa.reportmanager.Report;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.test.qa.driver.*;

import java.time.Duration;


import static com.test.qa.configurations.Configuration.*;

public class HomePage {

	WebDriver driver;
	public By loginIcon = By.xpath("(//*[@class='relative z-50 ']//button[@class='block self-end p-1'])[1]");
	public By registerLink = By.xpath("//*[@class='button textLink ']");
	public By userNameField = By.xpath("//*[@name='username']");
	public By passwordField = By.xpath("//*[@name='password']");
	public By logInBtn=By.xpath("//button//span[contains(text(),'Log in')]");
	public By checkboxNew = By.xpath("//*[@class='absolute top-0 left-0 w-full h-full opacity-0 peer  cursor-pointer']");
	public By signUpButton = By.xpath("//*[@class='button primaryOnLight w-full']");
	public By loggedInText = By.xpath("//h4[@class='pb-5']");

	public By txtUserName = By.name("userName");
	public By txtPassword = By.name("password");
	public By btnSubmit = By.name("submit");
	public By searchInput = By.xpath("//*[@class='aa-Input']");
	public By searchIcon = By.xpath("//*[@class='aa-SubmitButton']");
	public By searchResult = By.xpath("//ul[contains(@class, 'grid grid-')]/li[2]");
	public By productName=By.xpath("(//*[contains(@class,'ProductTile_productName')])[2]");
	public By productPrice=By.xpath("(//*[contains(@class,'ProductTile_productName')])[2]/following-sibling::h4");

	public By codeButton=By.id("regverify");
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * Login with valid credentials
	 * 
	 * @param userName
	 * @param password
	 * @throws Exception
	 */
	public void login(String userName, String password) throws Exception {
		driver.get(BASE_URL);
		DriverManager.getWait(60).until((ExpectedCondition<Boolean>) wd ->
				((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
		Report.log(Status.PASS, "Navigated to the home page");
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(loginIcon)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(loginIcon));
		driver.findElement(loginIcon).click();
//		driver.findElement(loginIcon).click();
		driver.findElement(userNameField).sendKeys(userName);
		driver.findElement(logInBtn).click();
		driver.findElement(passwordField).sendKeys(password);
		driver.findElement(logInBtn).click();
		DriverManager.getWait(60).until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(loginIcon)));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(loginIcon));
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(loggedInText)));
		String actualText = driver.findElement(loggedInText).getText();
		Assert.assertTrue(actualText.contains("You are logged in!"), "The text does not contain 'You are logged in!'");		Report.log(Status.PASS, "Logged In text is displayed in home screen");
	}

	public void registration(String userName, String password) throws Exception {
		driver.get(BASE_URL);
		Report.log(Status.PASS, "Navigated to the home page");
		driver.findElement(loginIcon).click();
		String uniqueID = UUID.randomUUID().toString().substring(0, 5);
		String user=uniqueID+"_" +userName;
		driver.findElement(userNameField).sendKeys(user);
		driver.findElement(registerLink).click();
		driver.findElement(passwordField).sendKeys(password);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", driver.findElement(checkboxNew));
		driver.findElement(checkboxNew).click();
		Thread.sleep(2000);
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(signUpButton)));
		driver.findElement(signUpButton).click();
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(codeButton)));
		Report.log(Status.PASS, "Authentication code is sent to the mail.");
	}
	public Map<String, String> searchProduct(String search) throws Exception {
		driver.get("https://www.xcite.com/");
		Thread.sleep(1000);

		DriverManager.getWait(60).until((ExpectedCondition<Boolean>) wd ->
				((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(searchInput)));
		driver.findElement(searchInput).sendKeys(search);
		DriverManager.getWait(60).until(ExpectedConditions.elementToBeClickable(driver.findElement(searchIcon)));
		driver.findElement(searchIcon).click();
		DriverManager.getWait(60).until((ExpectedCondition<Boolean>) wd ->
				((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete") &&
						wd.getCurrentUrl().equals("https://www.xcite.com/search?q=iPhone")
		);
		Assert.assertEquals(getPageCurrentUrl(), "https://www.xcite.com/search?q=iPhone");
		String productNameSearch = driver.findElement(productName).getText();
		String productPriceSearch = driver.findElement(productPrice).getText();
		Map<String, String> productDetails = new HashMap<>();
		productDetails.put("productName", productNameSearch);
		productDetails.put("productPrice", productPriceSearch);
		driver.findElement(searchResult).click();
		Thread.sleep(1000);
		return productDetails;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getPageCurrentUrl() throws Exception {
		return driver.getCurrentUrl();
	}
}
