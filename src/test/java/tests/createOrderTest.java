package tests;

import Base.BaseTest;
import io.restassured.response.Response;
import utils.TestUtils;

import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class createOrderTest extends BaseTest {
  public static String accessToken;
  public static String userId;
  public static String variantId;
  public static String facilityId;
  public static String orderId;
  public static String orderNumber;
	
	
    @Test(priority = 7)
    public void createOrder_Success() {
    	
    	  // Fetch values from previous tests
        String userId = VerifyOtpTest.userId;
        String accessToken = VerifyOtpTest.accessToken;
        String variantId = ListAllVehicleVariantsTest.variantId;
        String facilityId = getNearestDealerTest.facilityId;

        int pincode = TestUtils.randomIndianPincode();
    	

    	String createOrderBody =
    	"{\n" +
    	"  \"query\": \"mutation CreateOrder($userId: String!, $variantId: String!, $city: String, $state: String, $pincode: Int, $facilityId: String) {" +
    	"createOrder(userId: $userId, variantId: $variantId, city: $city, state: $state, pincode: $pincode, facilityId: $facilityId) {" +
    	"success message data { orderId orderNumber status city amount } errors { message } } }\",\n" +

    	"  \"variables\": {\n" +
    	"    \"userId\": \"" + userId + "\",\n" +
    	"    \"variantId\": \"" + variantId + "\",\n" +
    	"    \"facilityId\": \"" + facilityId + "\",\n" +
    	"    \"pincode\": " + pincode + ",\n" +
    	"    \"state\": \"Karnataka\",\n" +
    	"    \"city\": \"Bangalore\"\n" +
    	"  }\n" +
    	"}";

    	Response createOrderResponse =
    	        given()
    	            .log().all()
    	            .header("Authorization", "Bearer " + accessToken)
    	            .header("x-operation-name", "createOrder")
    	            .body(createOrderBody)
    	        .when()
    	            .post("/stage/api/graphql/order")
    	        .then()
    	            .log().all()
    	            .statusCode(200)
    	            .extract().response();
    	
    	orderId=createOrderResponse.jsonPath().getString("data.createOrder.data.orderId");
    	orderNumber=createOrderResponse.jsonPath().getString("data.createOrder.data.orderNumber");
    	
    	
    	Assert.assertTrue(
    		    createOrderResponse.jsonPath().getBoolean("data.createOrder.success")
    		);
    	
    	 Assert.assertEquals(
    			 createOrderResponse.jsonPath().getString("data.createOrder.message"),
                 "Order created successfully"
         );

    		Assert.assertNotNull(
    		    createOrderResponse.jsonPath().getString("data.createOrder.data.orderId")
    		);
    }
}