package tests;

import Base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class getUserDetailsTest extends BaseTest {

    @Test(priority = 11)
    public void getUserDetails_Success() {

        String accessToken = VerifyOtpTest.accessToken;
        String userId = VerifyOtpTest.userId;

        String requestBody =
        "{\n" +
        "  \"query\": \"query GetUserDetails($getUserDetailsId: ID) { " +
        "getUserDetails(id: $getUserDetailsId) { " +
        "success message data { userId id firstname lastname name email mobile primaryVin profilePicture signupSource emailVerified whatsappConsent pincode createdAt updatedAt } " +
        "errors { errorCode message statusCode } } }\",\n" +

        "  \"variables\": {\n" +
        "    \"getUserDetailsId\": \"" + userId + "\"\n" +
        "  }\n" +
        "}";

        Response response =
                given()
                        .log().all()
                        .header("Authorization", "Bearer " + accessToken)
                        .header("x-operation-name", "getUserDetails")
                        .body(requestBody)
                .when()
                        .post("/stage/api/graphql/user")
                .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response();

        // Assertions
        Assert.assertTrue(
                response.jsonPath().getBoolean("data.getUserDetails.success")
        );

        Assert.assertEquals(
                response.jsonPath().getString("data.getUserDetails.data.userId"),
                userId
        );

        Assert.assertNotNull(
                response.jsonPath().getString("data.getUserDetails.data.mobile")
        );

        Assert.assertNull(
                response.jsonPath().get("data.getUserDetails.errors")
        );
    }
}