package com.test.qa.tests;
import org.testng.ITestContext;
import com.test.qa.pageobjects.CartPage;
import com.test.qa.pageobjects.PDP;
import com.test.qa.pageobjects.ShippingPage;
import com.test.qa.utilities.TestListener;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.HashMap;
import java.util.List;
import com.aventstack.extentreports.Status;
import com.codoid.products.exception.FilloException;
import com.test.qa.configurations.Configuration;
import com.test.qa.driver.DriverManager;
import com.test.qa.pageobjects.HomePage;
import com.test.qa.pageobjects.*;
import com.test.qa.reportmanager.Report;
import com.test.qa.utilities.ExcelManager;

@Listeners(TestListener.class)

public class CheckoutFlow extends BaseTest {

	@Test(dataProvider = "userData",priority = 1)
	public void Checkout(String testCaseName,String username, String password, String search,ITestContext context) throws Exception {
		context.setAttribute("testName", testCaseName);
		HomePage obj = new HomePage(DriverManager.getDriver());
		CartPage objCart = new CartPage(DriverManager.getDriver());

		if(search!=""){
			Report.log(Status.PASS, "Checkout flow started for Registered User ");
			obj.login(username, password);
			Thread.sleep(3000);
			DriverManager.getDriver().get("https://www.xcite.com/checkout/cart");
			objCart.emptyCart();
			Map<String, String> productDetails = new HashMap<>();
			productDetails=obj.searchProduct(search);
			Report.log(Status.PASS, "Search is successful");
			Report.log(Status.PASS, "Product details are"+productDetails);
			Thread.sleep(1000);
			PDP objPdp = new PDP(DriverManager.getDriver());
			objPdp.addToCart(productDetails);
			Thread.sleep(1000);
			objCart.increaseQty(productDetails);
			Thread.sleep(1000);
			ShippingPage objShip=new ShippingPage(DriverManager.getDriver());
			objShip.fillMapAndShipping();
         }
		else{
			Report.log(Status.PASS, "New User Registration ");
			obj.registration(username, password);
			//do registration and exit
			return;
		}

	}
	@DataProvider
	public Object[][] userData() throws FilloException {
		ExcelManager fillo = new ExcelManager();
		List<HashMap<String, String>> users = fillo.getAllData(Configuration.TEST_RESOURCE_PATH, "testdata.xls", "TestData");
		int numColumns = users.get(0).size();
		Object[][] dataObj = new Object[users.size()][numColumns];
		System.out.println("No of coulmns is"+ numColumns);
		for (int i = 0; i < users.size(); i++) {
			dataObj[i][0] = users.get(i).get("TestCaseName");
			dataObj[i][1] = users.get(i).get("Username");
			dataObj[i][2] = users.get(i).get("Password");
			dataObj[i][3] = users.get(i).get("Search");
		}
		return dataObj;
	}
}
