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
}