package com.test.qa.tests;

import com.test.qa.utilities.TestListener;
import org.testng.annotations.*;

import com.test.qa.driver.DriverManager;

@Listeners(TestListener.class)

public class BaseTest {

	/**
	 * This method initializes the driver and launches browser. It maximizes the browser window.
	 * It is called before each test.
	 * 
	 * @param browser
	 */
	@Parameters({ "browser" })
	@BeforeMethod
	public void init(@Optional("chrome") String browser)  {
		DriverManager.initialize(browser);
	}
	
	/**
	 * quit() method is called after every test. It closes the browser
	 * 
	 */
	@AfterMethod
	public void quit() {
//		DriverManager.quit();
	
	}
	
	/**
	 * terminate() method is called after every class. It removes the ThreadLocal driver.
	 */
	@AfterClass
	public void tearDown() {
		DriverManager.quit();
		DriverManager.terminate();
	}

}
