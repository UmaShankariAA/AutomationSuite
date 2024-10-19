package api.tests;

import api.basetest.BaseTest;
import api.endpoints.BookStoreEndPoints;
import api.payloads.DataProviders;
import api.payloads.MapPayload;
import api.payloads.RequestBodyBuilder;
import api.utils.RandomDataGenerator;
import com.aventstack.extentreports.Status;
import com.test.qa.utilities.TestListener;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.test.qa.reportmanager.Report;


import java.io.IOException;
import java.util.*;

@Listeners(TestListener.class)

public class ValidateBooks {
    String authToken;
    String userName;
    String userId;
    String bookId;

    MapPayload testData = new MapPayload(authToken, userId, bookId);
    RandomDataGenerator data = new RandomDataGenerator();
    private String username = data.generateRandomUsername();
    private String password = data.generateRandomPassword();
    private String jsonString;
    private String modifiedJsonString;

    @Test (priority = 0)       //initial step to get auth token, username, and userId ( to uitlize on other test cases )
    public void createUserAndAuthorize() {
        System.out.println(username + "   " + "A"+password);
        Map<String, String> userMap = BaseTest.createUser(userName, "A"+password);
        authToken = userMap.get("authToken");
        userName = userMap.get("username");
        userId = userMap.get("userId");
        Report.log(Status.PASS, "User creation is successful");

    }


    @Test(priority = 1)
    public void validateAllBooks() throws JSONException {
        Response response = BookStoreEndPoints.getAllBooks(authToken);
        Assert.assertEquals(response.getStatusCode(), 200);
        Report.log(Status.PASS, "Get all books API is successful ");
        Assert.assertEquals(response.getHeader("content-type"), "application/json; charset=utf-8");
         jsonString = response.asString();
        JSONAssert.assertEquals(RequestBodyBuilder.allBookAPIResponse, jsonString, true);
        Report.log(Status.PASS, "Json response structure matche the expected string format ");
        List<Map<String, String>> books = JsonPath.from(jsonString).get("books");
        Assert.assertTrue(books.size() > 0);
        bookId = books.get(1).get("isbn");
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray booksArray = jsonObject.getJSONArray("books");
        JSONObject modifiedJson = new JSONObject();
        // Step 4: Add the "userId" field
        modifiedJson.put("userId", userId);
        // Step 5: Add the "collectionOfIsbns" key and assign the books array to it
        modifiedJson.put("collectionOfIsbns", booksArray);
        Report.log(Status.PASS, "Json output from the get all books APIs: "+jsonString);
        Report.log(Status.PASS, "Modified json as per the add books reuest body requirement : "+modifiedJson);


        // Step 6: Convert the modified JSON object to a string
         modifiedJsonString = modifiedJson.toString(4);  // Pretty-printing

        // Print the modified JSON string
        System.out.println(modifiedJsonString);
        System.out.println(" book ID ----> "  + bookId   );
        System.out.println(" jsonString ----> "  + jsonString   );
        /*  List<Map<String, Object>> bookList = new ArrayList<>();*/
    }

    @Test(priority = 2)
    public void validateAddBook() {
        System.out.println("add book api ----> " + userId + bookId +"    auth token   " + authToken);
        Response response = BookStoreEndPoints.addBook(authToken, modifiedJsonString);
        System.out.println(response.getStatusCode() + "  " + response.getHeaders());
        Assert.assertEquals(response.getStatusCode(), 201);
        Report.log(Status.PASS, "Books are added to user successfully ");


    }
//    @Test(priority = 2)
//    public void validateGetBook() {
//        Response response = BookStoreEndPoints.getBookData(testData.getHeaders(), bookId);
//        System.out.println("getBook response ---> " + response);
//        Assert.assertEquals(response.getStatusCode(), 200);
//
//        String[] fields = {"isbn", "title", "subTitle", "author", "website"};
//        Map<String, String> resposeMap = new LinkedHashMap<>();
//        for (String field : fields) {
//            String value = response.getBody().jsonPath().get(field);
//            resposeMap.put(field, value);
//        }
//    }
//
//    @Test(priority = 3)
//    public void updateBook() {
//        MapPayload testData = new MapPayload(authToken, userId, bookId); // Pass the authToken to TestData
//        Map<String, Object> headers = testData.getHeaders();
//        Map<String, Object> pathParams = testData.getPathParams();
//        Map<String, Object> payload = testData.getPayload();
//        Response response = BookStoreEndPoints.updateBook(headers, pathParams, payload);
//        Assert.assertEquals(response.getStatusCode(), 200);
//        String isbnno = response.jsonPath().get("books[0].isbn");
//        Assert.assertEquals(isbnno, bookId);
//
//    }


}
