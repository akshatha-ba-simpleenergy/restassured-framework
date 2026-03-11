package tests;

import Base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class test_signatureTest extends BaseTest {

	public static String signature;
	public static String timestamp;
	
    @Test(priority = 8)
    public void testsignature_Success() {

        String orderNumber = createOrderTest.orderNumber;

        String requestBody =
        "{\n" +
        "  \"type\": \"PAYMENT_SUCCESS_WEBHOOK\",\n" +
        "  \"event_time\": \"2026-03-10T06:00:06.967Z\",\n" +
        "  \"data\": {\n" +
        "    \"order\": {\n" +
        "      \"order_id\": \"" + orderNumber + "\",\n" +
        "      \"order_amount\": 1947,\n" +
        "      \"order_currency\": \"INR\",\n" +
        "      \"order_tags\": null\n" +
        "    },\n" +
        "    \"payment\": {\n" +
        "      \"cf_payment_id\": \"CF1234567890\",\n" +
        "      \"payment_status\": \"SUCCESS\",\n" +
        "      \"payment_amount\": 1947,\n" +
        "      \"payment_currency\": \"INR\",\n" +
        "      \"payment_message\": \"Payment completed successfully\",\n" +
        "      \"payment_time\": \"2025-09-11T06:00:06.967Z\",\n" +
        "      \"bank_reference\": \"BANKTXN987654321\",\n" +
        "      \"payment_method\": {\n" +
        "        \"upi\": {\n" +
        "          \"channel\": \"collect\",\n" +
        "          \"upi_id\": \"user@upi\",\n" +
        "          \"upi_instrument\": \"UPI_CREDIT_CARD\",\n" +
        "          \"upi_payer_ifsc\": \"SBI0025434\",\n" +
        "          \"upi_payer_account_number\": \"XXXXX0231\"\n" +
        "        }\n" +
        "      },\n" +
        "      \"payment_group\": \"upi\"\n" +
        "    },\n" +
        "    \"customer_details\": {\n" +
        "      \"customer_name\": \"Alice\",\n" +
        "      \"customer_id\": \"00cd1ebe-0655-4d95-ab6f-f5fd59bc804a\",\n" +
        "      \"customer_email\": \"alice@example.com\",\n" +
        "      \"customer_phone\": \"+918721801866\"\n" +
        "    },\n" +
        "    \"payment_gateway_details\": {\n" +
        "      \"gateway_name\": \"CASHFREE\",\n" +
        "      \"gateway_order_id\": \"1634766330\",\n" +
        "      \"gateway_payment_id\": \"1504280029\",\n" +
        "      \"gateway_order_reference_id\": \"abc_124\",\n" +
        "      \"gateway_settlement\": \"CASHFREE\"\n" +
        "    },\n" +
        "    \"terminal_details\": {\n" +
        "      \"cf_terminal_id\": 17269,\n" +
        "      \"terminal_phone\": \"8971520311\"\n" +
        "    }\n" +
        "  }\n" +
        "}";

        Response response =
                given()
                        .log().all()
                        .baseUri(BaseTest.paymentUrl)
                        .header("Content-Type", "application/json")
                        .header("x-api-key" , "ALMcHSg0Em8pnIt8AYEDM36ODk5MIB4o5VcolfX0")
                        .header("x-webhook-signature" ,"t+cv3BoIHX5D1t7DbuD1T4JZ8GCId1a6ODDa0/koTtc=")
                        .header("x-webhook-timestamp", "2026-03-10T06:00:06.967Z")
                         
                        .body(requestBody)
                .when()
                        .post("/api/v1/order/cashfree/test-signature")
                .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response();
        
        signature=response.jsonPath().getString("signature");
        timestamp=response.jsonPath().getString("timestamp");

        Assert.assertNotNull(response);
    }
}