import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.json.JSONObject;

public class RestAssuredTest {


    //Rest
    /*
     * Kita bakal hit API "https://jsonplaceholder.typicode.com/photos"
     * Dengan method GET
     * Kemudian prinout statuscode
     * prinout responsenya
     */

    //Test
    /*
     * Hit API "https://jsonplaceholder.typicode.com/photos"
     * 1. Validasi status code 200
     * 2. Printout data ke 2
     */


    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/photos";
    }

    @Test
    public void GetPhoto(){
        // Define URL
        // RestAssured.baseURI = "https://jsonplaceholder.typicode.com/photos";
        RequestSpecification httpRequest = RestAssured.given();

        Response response = httpRequest.request(Method.GET,"");
       
        System.out.println("Response nya : " + response.asPrettyString());
        System.out.println("status code : "+ response.statusCode());

        Assert.assertEquals(response.getStatusCode(), 200);
    }


    /*
     * Scenario test:
     * 1. Hit API https://jsonplaceholder.typicode.com/photos
     * 2. Verify data id = 5,
     * - title : natus nisi omnis corporis facere molestiae rerum in
     * - url : https://via.placeholder.com/600/f66b97
     * 
     */

    @Test
    public void GetPhotoParams(){
        // Define URL
        // RestAssured.baseURI = "https://jsonplaceholder.typicode.com/photos";
        RequestSpecification httpRequest = RestAssured.given().param("id", 5);

        Response response = httpRequest.request(Method.GET,"");

        JsonPath jsonPathEvaluator = response.jsonPath();

        ArrayList<String> title = jsonPathEvaluator.get("thumbnailUrl");

        ArrayList<String> url = jsonPathEvaluator.get("url");

        System.out.println("Response nya : " + response.asPrettyString());

        System.out.println("title adalah : " + title);

        System.out.println("url nya : "+ url);

        Assert.assertEquals(title.get(0), "natus nisi omnis corporis facere molestiae rerum in");

        Assert.assertEquals(url.get(0), "https://via.placeholder.com/600/f66b97");
    }

    /*
     * 1. Verifikasi success setelah user publish new data
     */

    @Test
    public void postData(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        RequestSpecification httpRequest = RestAssured.given();

        // Body request
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "minibootcamp");
        requestParams.put("body", "requestParams");
        requestParams.put("userId", 1);

        httpRequest.body(requestParams.toString());
        Response response = httpRequest.post("/posts");
        System.out.println("Response: " + response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 201);

    } 
}
