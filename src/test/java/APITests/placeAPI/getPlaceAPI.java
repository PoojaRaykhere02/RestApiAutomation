package APITests.placeAPI;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class getPlaceAPI {

    @Test
            public void getPlaceAPITest(){
        given().
                baseUri("https://rahulshettyacademy.com").
                queryParam("key","qaclick123").
                queryParam("place_id", "2a3334351f4968ead9c528fe8600728d")
                .header("Content-Type","application/json").
                when().
                get("/maps/api/place/get/json").
                then().log().all().assertThat().statusCode(200).body("name",equalTo("Frontline house"));
     }
}
