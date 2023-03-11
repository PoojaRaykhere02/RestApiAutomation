package APITests.reqresAPI;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PutRequest {

    @Test
    public void putRequestTest(){
        String newName= "Pooja";
        String payload = "{\n" +
                "    \"name\": \"pooja1\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}\n";
        given().
                baseUri("https://reqres.in").
                body(payload).
//                body("{\n" +
//                        "    \"name\": \""+newName+"\",\n" +
//                        "    \"job\": \"leader\"\n" +
//                        "}\n").
                when().
                put("/api/users/2").
                then().statusCode(200);

    }
}
