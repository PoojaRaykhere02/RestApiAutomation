package APITests.libraryAPI;

import generic.DataProviderClass;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static generic.genericUtility.rawToJson;
import static generic.utility.randomNumber;
import static io.restassured.RestAssured.given;
import static payload.libraryPayload.addBook;

public class libraryTest {
    String isbn, aisle, bookId;
    String response;


    @BeforeClass
    public void getURL(){
        RestAssured.baseURI = "https://rahulshettyacademy.com";
    }

    /* verify book is successfully added
    * */
    @Test
    public void addBookAPI(){
        isbn= "book"+ randomNumber();;
        aisle = randomNumber();
         given().
                body(addBook(isbn, aisle)).
                when().
                post("/Library/Addbook.php").
                then().log().all().assertThat().statusCode(200);
    }

    /* verify response id
    * Dynamically build JSON payload with external data sets */
    @Test
    public void getBookId(){
        isbn= "book"+ randomNumber();;
        aisle = randomNumber();
        String response= given().
                body(addBook(isbn, aisle)).
                when().
                post("/Library/Addbook.php").
                then().log().all().assertThat().extract().response().asString();

        JsonPath jsonPath= rawToJson(response);
         bookId = jsonPath.get("ID");
        System.out.println("bookId= " + bookId);
    }


    /* delete added book
     * resource: /Library/DeleteBook.php
     * */
    @Test(dependsOnMethods = "getBookId")
    public void deleteAddedData(){
        String requestBody = "{\n" +
                "    \"ID\": \""+bookId+"\"\n" +
                "}";
        given().
                body(requestBody).
                when().
                delete("/Library/DeleteBook.php").
                then().log().all().assertThat().statusCode(200).extract().response().asString();
    }

    @Test(dataProvider = "BooksData", dataProviderClass = DataProviderClass.class)
    public void getBookIdWithMultipleDataSets(String isbn, String aisle){
        //add books
        response= given().
                body(addBook(isbn, aisle)).
                when().
                post("/Library/Addbook.php").
                then().assertThat().extract().response().asString();

        JsonPath jsonPath= rawToJson(response);
        String bookIds = jsonPath.get("ID");
        System.out.println("bookId= " + bookIds);

        //delete books
               given().
                body(response).
                when().
                delete("/Library/DeleteBook.php").
                then().log().all().assertThat().statusCode(200).extract().response().asString();
    }

}
