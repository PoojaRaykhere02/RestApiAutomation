package APITests.reqresAPI;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteRequest {


    @Test
    public void deleteRequestTest() {
        given().
                baseUri("https://reqres.in/").
                when().
                delete("/api/users/2").
                then().statusCode(204);
    }
}