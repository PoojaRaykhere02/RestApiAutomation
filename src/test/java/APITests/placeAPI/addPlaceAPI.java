package APITests.placeAPI;

import org.testng.annotations.Test;
import payload.addPlacePayload;

import static io.restassured.RestAssured.given;

public class addPlaceAPI {

    @Test
    public void addPalceAPI(){
         given().
                baseUri("https://rahulshettyacademy.com").
                queryParam("key", "qaclick123").
                header("Content-Type", "application/json").
                body(addPlacePayload.AddPlace()).
                when().
                post("/maps/api/place/add/json").
                then().log().all().statusCode(200);
    }
}
