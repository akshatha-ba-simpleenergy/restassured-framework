package tests;

import io.restassured.response.Response;
import utils.TestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import Base.BaseTest;

import static io.restassured.RestAssured.*;

public class LoginTest extends BaseTest {

    @Test (priority=1)
    public void loginWithMobile_SendOtp_Success() {

    	String mobile = TestUtils.getMobile();

    	String requestBody = "{\n" +
    	        "  \"query\": \"mutation login($mobile: String!, $whatsappConsent: Boolean!) { " +
    	        "login(mobile: $mobile, whatsappConsent: $whatsappConsent) { message success } }\",\n" +
    	        "  \"variables\": {\n" +
    	        "    \"mobile\": \"" + mobile + "\",\n" +
    	        "    \"whatsappConsent\": true\n" +
    	        "  }\n" +
    	        "}";

        Response response =
                given()
                .log().all()
                .header("x-api-key", "bGV0bWVsaXZlaW5wZWFjZQ==")
                    .header("x-operation-name", "login")   
                    .body(requestBody)
                .when()
                    .post("/stage/api/graphql/user")
                .then()
                .log().all()
                    .statusCode(200)
                    .extract().response();
        
 

                   

        // -------- Assertions --------
        Assert.assertTrue(response.jsonPath().getBoolean("data.login.success"));
        
      
       

        Assert.assertEquals(
                response.jsonPath().getString("data.login.message"),
                "OTP sent successfully. Please verify OTP to complete login."
        );
        Assert.assertNull(response.jsonPath().get("data.login.errors"));
    }
}
