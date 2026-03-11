package tests;

import Base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class ListAccessoriesTest extends BaseTest {

    @Test(priority = 5)
    public void listAccessories_Success() {

        String requestBody = "{\n" +
                "  \"query\": \"query ListAccessories { listAccessories { success message data { id name } errors { message status success keyword } } }\"\n" +
                "}";

        Response response =
                given()
                    .log().all()
                    .header("x-api-key" ,"bGV0bWVsaXZlaW5wZWFjZQ==")
                    .header("x-operation-name", "listAccessories")
                    .body(requestBody)
                .when()
                    .post("/stage/api/graphql/order")
                .then()
                    .log().all()
                    .statusCode(200)
                    .extract().response();

        // Assertions
        Assert.assertTrue(
                response.jsonPath().getBoolean("data.listAccessories.success")
        );

        Assert.assertNotNull(
                response.jsonPath().getList("data.listAccessories.data")
        );

        Assert.assertNull(
                response.jsonPath().get("data.listAccessories.errors")
        );

        // Optional validation
        Assert.assertNotNull(
                response.jsonPath().getString("data.listAccessories.data[0].id")
        );

        Assert.assertNotNull(
                response.jsonPath().getString("data.listAccessories.data[0].name")
        );
    }
}