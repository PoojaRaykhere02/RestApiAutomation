package APITests.jiraAPIs;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.sessionId;

public class JiraTests {
        String comment,commentId;
    SessionFilter sessionFilter = new SessionFilter();

@BeforeClass
    public void getSessionId(){
    RestAssured.baseURI = "http://localhost:8080";
                given().
                        header("Content-Type","application/json").
                        body("{ \"username\": \"poojaraykhere26\", \"password\": \"Loyalisme12@\" }\n").
                        filter(sessionFilter).
                when().
                        post("/rest/auth/1/session").
                then().log().all().extract().response().asString();
}

    @Test
    public void addCommentInJiraIssue(){
        comment = "adding comments using rest api automation";
       String response= given().
                pathParam("key","10001").log().all().
                header("Content-Type","application/json").
                body("{\n" +
                        "    \"body\": \""+comment+"\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}").filter(sessionFilter).
                when().
                    post("/rest/api/2/issue/{key}/comment").
                then().log().all().assertThat().statusCode(201).extract().response().asString();
       JsonPath jsonPath = new JsonPath(response);
        commentId = jsonPath.getString("id");
    }

    @Test
    public void addAttachment(){
        given().
                header("X-Atlassian-Token","no-check").
                filter(sessionFilter).
                pathParam("key", "10001").
                header("Content-Type","multipart/form-data").
                multiPart("file", new File("jiraTextFile")).
                when().
                post("/rest/api/2/issue/{key}/attachments").
                then().log().all().assertThat().statusCode(200);
    }

    @Test
    public void getIssue(){
        String issueDetails = given().
                pathParam("key", "10001").
                queryParam("fields","comment").
                filter(sessionFilter).
                when().
                get("/rest/api/2/issue/{key}").
                then().log().all().extract().response().asString();
        System.out.println("issueDetails = " + issueDetails);
    }

    @Test(dependsOnMethods = "addCommentInJiraIssue")
    public void getAllTheCommentIds(){
        String issueDetails = given().
                pathParam("key", "10001").
                queryParam("fields","comment").
                filter(sessionFilter).
                when().
                get("/rest/api/2/issue/{key}").
                then().log().all().extract().response().asString();
        System.out.println("issueDetails = " + issueDetails);

        JsonPath jsonPath = new JsonPath(issueDetails);
        int commentsCount = jsonPath.getInt("fields.comment.comments.size()");
        for(int i=0;i<commentsCount ; i++) {
            String commentIdIssue = jsonPath.get("fields.comment.comments[" + i + "].id").toString();
            if (commentIdIssue.equalsIgnoreCase(commentId)) {
                String message = jsonPath.get("fields.comment.comments[" + i + "].body").toString();
                System.out.println("message= " + message);
                Assert.assertEquals(message, comment);
            }
        }
    }
}
