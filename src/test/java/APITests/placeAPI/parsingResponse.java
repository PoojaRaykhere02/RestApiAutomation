package APITests.placeAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import payload.addPlacePayload;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class parsingResponse {
@Test
    public void parsingResponseUsingJsonPath(){
    //E2E scenario: Add place--> update place with new address --> get place to validate if new address is updated or not?

    //Add place in request body and Get place id
    RestAssured.baseURI = "https://rahulshettyacademy.com";

     String response= given().
                 queryParam("key","qaclick123").
                 header("Content-Type","application/json").
                 body(addPlacePayload.AddPlace()).
             when().
                post("/maps/api/place/add/json").
             then().assertThat().statusCode(200).extract().response().asString();

    JsonPath jsonPath=new JsonPath(response);
    String place_id= jsonPath.getString("place_id");
    System.out.println("place_id= " + place_id);

    //Update place
    String newAddress = "Edinburgh UK";
     given().
            log().all().
            queryParam("key","qaclick123").
            header("Content-Type","application/json").
            body("{\n" +
                    "\"place_id\":\""+place_id+"\",\n" +
                    "\"address\":\""+newAddress+"\",\n" +
                    "\"key\":\"qaclick123\"\n" +
                    "}\n").
            when().
            put("/maps/api/place/update/json").
            then().log().all().
             statusCode(200).
             body("msg",equalTo("Address successfully updated"));


     //Get place
            String getResponse = given().
                    log().all().
                    queryParam("key","qaclick123").
                    queryParam("place_id",place_id).
                     when().
                    get("/maps/api/place/get/json").
                    then().log().all().assertThat().statusCode(200).extract().response().asString();
            JsonPath js2= new JsonPath(getResponse);
            String getUpdatedAddress = js2.getString("address");
            System.out.println("getUpdatedAddress= " + getUpdatedAddress);
            System.out.println("getUpdatedAddress= " + getUpdatedAddress);
            Assert.assertEquals(newAddress, getUpdatedAddress);
    }
}
