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
}