package apitests;

import apitests.models.Post;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class GetTests extends BaseApiTest {

    @Test
    public void testGetPostById() {
        Response response = requestSpec
                .get("/posts/1");

        response.prettyPrint(); // just to visualize

        assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void verifyPostTitleIsNotNull() {
        Response response = requestSpec
                .when()
                .get("/posts/1")
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
        requestSpec.when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    public void testGetPostAndDeserialize() {
        Response response = requestSpec.get("/posts/1");

        // Deserialize to Post class
        Post post = response.as(Post.class);

        assertEquals(post.id, 1);
        assertEquals(post.userId, 1);
    }
}