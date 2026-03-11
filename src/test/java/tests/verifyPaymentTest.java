package tests;

import Base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class verifyPaymentTest extends BaseTest {

	
	
    @Test(priority = 10)
    public void verifyPayment_Success() {
    	
    	// Get input from response
        String accessToken = VerifyOtpTest.accessToken;
        String orderId = createOrderTest.orderId;

        String requestBody =
        "{\n" +
        "  \"query\": \"mutation VerifyPayment($orderId: ID!) { " +
        "verifyPayment(orderId: $orderId) { " +
        "success message data { cashfreeOrderStatus orderStatus cashfreePaymentStatus } " +
        "errors { message status success keyword } } }\",\n" +

        "  \"variables\": {\n" +
        "    \"orderId\": \"" + orderId + "\"\n" +
        "  }\n" +
        "}";

        Response response =
                given()
                        .log().all()
                        .header("Authorization", "Bearer " + accessToken)
                        .header("x-operation-name", "verifyPayment")
                        .body(requestBody)
                .when()
                        .post("/stage/api/graphql/order")
                .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response();

        // Assertions
        Assert.assertTrue(
                response.jsonPath().getBoolean("data.verifyPayment.success")
        );

        Assert.assertNotNull(
                response.jsonPath().getString("data.verifyPayment.data.cashfreeOrderStatus")
        );

        Assert.assertNotNull(
                response.jsonPath().getString("data.verifyPayment.data.orderStatus")
        );

        Assert.assertNotNull(
                response.jsonPath().getString("data.verifyPayment.data.cashfreePaymentStatus")
        );

        Assert.assertNull(
                response.jsonPath().get("data.verifyPayment.errors")
        );
    }
}