package tests;

import Base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class startPaymentTest extends BaseTest {

    @Test(priority = 8)
    public void startPayment_Success() {

    	
    	//fetch value from previous test
        String accessToken = VerifyOtpTest.accessToken;
        String orderId = createOrderTest.orderId;
      

        String requestBody =
                "{\n" +
                "  \"query\": \"mutation StartPayment($orderId: ID!) { " +
                "startPayment(orderId: $orderId) { " +
                "success message cashfreeToken expiresAt " +
                "errors { message status success keyword } } }\",\n" +

                "  \"variables\": {\n" +
                "    \"orderId\": \"" + orderId + "\"\n" +
                "  }\n" +
                "}";

        Response response =
                given()
                        .log().all()
                        .header("Authorization", "Bearer " + accessToken)
                        .header("x-operation-name", "startPayment")
                        .body(requestBody)
                .when()
                        .post("/stage/api/graphql/order")
                .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response();

        // Assertions
        Assert.assertTrue(
                response.jsonPath().getBoolean("data.startPayment.success")
        );

        Assert.assertNotNull(
                response.jsonPath().getString("data.startPayment.cashfreeToken")
        );

        Assert.assertNotNull(
                response.jsonPath().getString("data.startPayment.expiresAt")
        );

        Assert.assertNull(
                response.jsonPath().get("data.startPayment.errors")
        );
    }
}