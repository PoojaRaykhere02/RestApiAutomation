package APITests.reqresAPI;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
//import org.testng.annotations.Test;

public class GetMethod {

    @Test
    public void validate_get_method(){
        given().                        //we need to describe the present state of the system(context)
                baseUri("https://reqres.in").
                contentType("application/json").
                when().                 //action, event which are going to happen
                    get("/api/users/2").
                then().         //outcome-result of the action that we took. validate the outcome
                    log().all().
                    assertThat().
                statusCode(200).
                body("data.id", equalTo(2));
    }

    @Test
    public void validate_get_email_method(){
       String response= given().
                baseUri("https://reqres.in").
                contentType("application/json").
                when().
                get("/api/users/2").
                then().assertThat().statusCode(200).log().all().
                extract().response().asString();
        JsonPath jpath= new JsonPath(response);
        String expectedEmail = jpath.getString("data.email");
        Assert.assertEquals("janet.weaver@reqres.in", expectedEmail);

    }

    @Test
    public void validate_get_single_user_method(){
        String response= given().
                baseUri("https://reqres.in").
                contentType("application/json").
                when().
                get("/api/users/2").
                then().assertThat().statusCode(200).log().all().extract().response().asString();
        JsonPath jpath= new JsonPath(response);
        String expectedEmail = jpath.getString("data.id");//JSONPath is a query language for JSON, similar to XPath for XML.
        // It allows you to select and extract data from a JSON document.
        // You use a JSONPath expression to traverse the path to an element in the JSON structure.
        Assert.assertEquals("2", expectedEmail);
    }
}
