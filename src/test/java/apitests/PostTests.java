package apitests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class PostTests {

    @Test
    public void verifyPostTitleIsNotNull() {
        Response response = RestAssured
                .given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String title = response.jsonPath().getString("title");
        System.out.println("Title: " + title);
        Assert.assertNotNull(title, "Title should not be null");
    }

/*    @Test
    public void failOnPurpose() {
        Assert.fail("Just checking if this runs");
    }*/

    @Test
    public void testGetExample() {
        RestAssured
                .given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    public void testCreatePost() {
        System.out.println("=== TEST IS RUNNING ===");

        Response response = RestAssured
                .given()
                .contentType("application/json")
                .body("{ \"title\": \"foo\", \"body\": \"bar\", \"userId\": 1 }")
                .post("https://jsonplaceholder.typicode.com/posts");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 201);
    }
}