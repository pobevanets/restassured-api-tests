package apitests;

import apitests.models.Post;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

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

    @Test
    public void getAllPostsAndDeserialize() {
        List<Post> posts = requestSpec
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<Post>>() {});

        // Print first 5 posts
        posts.stream()
                .limit(5)
                .forEach(System.out::println);
    }

    @Test
    public void getFirstPostTitleUsingPath() {
        String title = requestSpec
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract()
                .path("[0].title"); // JSON array -> 0th element -> title

        System.out.println("First post title: " + title);
    }

    @Test
    public void getAllPostIdsUsingJsonPath() {
        Response response = requestSpec
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Extract all "id" fields as a list of Integers
        var ids = response.jsonPath().getList("id", Integer.class);

        System.out.println("Total posts: " + ids.size());
        System.out.println("First 5 IDs: " + ids.subList(0, 5));
    }

    @Test
    public void getCommentsByPostId() {
        Response response = requestSpec
                .queryParam("postId", 1)
                .when()
                .get("/comments")
                .then()
                .statusCode(200)
                .extract()
                .response();

        List<Integer> postIds = response.jsonPath().getList("postId");
        System.out.println("All postIds in response: " + postIds);

        // Simple check
        boolean allMatch = postIds.stream().allMatch(id -> id == 1);
        System.out.println("All postIds are 1: " + allMatch);
    }

}