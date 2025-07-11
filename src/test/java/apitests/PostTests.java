package apitests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PostTests extends BaseApiTest {
    @Test
    public void testCreatePost() {
        String requestBody = """
                {
                  "title": "foo",
                  "body": "bar",
                  "userId": 1
                }
                """;

        Response response = requestSpec
                .body(requestBody)
                .post("/posts");

        response.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 201); // 201 Created
    }

    @Test
    public void createNewComment() {
        String requestBody = """
                {
                  "postId": 1,
                  "name": "Roman's Test",
                  "email": "roman@example.com",
                  "body": "This is a test comment from Rest Assured."
                }
                """;

        Response response = requestSpec
                .body(requestBody)
                .when()
                .post("/comments");

        // 🔎 Basic assertions
        Assert.assertEquals(response.statusCode(), 201, "Expected status code 201 for successful creation");

        // Optional: extract values from response
        String name = response.jsonPath().getString("name");
        String email = response.jsonPath().getString("email");

        Assert.assertEquals(name, "Roman's Test");
        Assert.assertEquals(email, "roman@example.com");
    }
}