package tests;

import Base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import static io.restassured.RestAssured.*;

public class VerifyOtpTest extends BaseTest {
	
	public static String userId;
	public static String accessToken;

    @Test(priority = 2)
    public void verifyOtp_LoginSuccess() {

        // Use same mobile number used during login
        String mobile=TestUtils.getMobile();

        // For testing, OTP is usually static or fetched from DB
        String otp = "3289"; // replace with valid OTP if needed

        String requestBody = "{\n" +
                "  \"query\": \"mutation VerifyOtp($mobile: String!, $otp: String!) { " +
                "verifyOtp(mobile: $mobile, otp: $otp) { data { accessToken refreshToken userId } message success errors { statusCode message errorCode } } }\",\n" +
                "  \"variables\": {\n" +
                "    \"mobile\": \"" + mobile + "\",\n" +
                "    \"otp\": \"" + otp + "\"\n" +
                "  }\n" +
                "}";

        Response response =
                given()
                 .log().all()
                    .header("Connection" , "keep-alive")
                    .header("x-api-key", "bGV0bWVsaXZlaW5wZWFjZQ==")
                    .header("x-operation-name", "verifyOtp")
                    .body(requestBody)
                .when()
                    .post("/stage/api/graphql/user")
                .then()
                    .log().all()        
                    .statusCode(200)
                    .extract().response();

        // -------- Assertions --------
        Assert.assertTrue(response.jsonPath().getBoolean("data.verifyOtp.success"));
        Assert.assertEquals(
                response.jsonPath().getString("data.verifyOtp.message"),
                "OTP verified and user logged in successfully."
        );
        
        userId = response.jsonPath().getString("data.verifyOtp.data.userId");
        accessToken  = response.jsonPath().get("data.verifyOtp.data.accessToken");
        // Token validation
        Assert.assertNotNull(response.jsonPath().getString("data.verifyOtp.data.accessToken"));
        Assert.assertNotNull(response.jsonPath().getString("data.verifyOtp.data.refreshToken"));
        Assert.assertNotNull(response.jsonPath().getString("data.verifyOtp.data.userId"));
    }
}
