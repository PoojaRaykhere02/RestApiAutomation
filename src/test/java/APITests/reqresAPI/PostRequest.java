package APITests.reqresAPI;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PostRequest {

@Test
    public void postRequestTest(){
        String payload = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader1\"\n" +
                "}\n";
           String response= given().
                    baseUri("https://reqres.in").header("Content-Type", "application/json").
                        body(payload).when().post("/api/users").
                    then().assertThat().log().all().statusCode(201).extract().response().asString();
    JsonPath jsonPath= new JsonPath(response);
    String name= jsonPath.getString("name");
    Assert.assertEquals(name, "morpheus");
    }
}
