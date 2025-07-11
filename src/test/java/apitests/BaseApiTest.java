package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {

    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        requestSpec = RestAssured
                .given()
                .contentType(ContentType.JSON);
    }
}
