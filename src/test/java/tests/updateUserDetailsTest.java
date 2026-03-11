
	package tests;

	import Base.BaseTest;
	import io.restassured.response.Response;
	import org.testng.Assert;
	import org.testng.annotations.Test;
	import utils.TestUtils;

	import static io.restassured.RestAssured.*;

	public class updateUserDetailsTest extends BaseTest {

	    @Test(priority = 3)
	    public void updateUser_Success() {

	        // Fetch userId from verifyOtp response
	        String userId = VerifyOtpTest.userId;
	        String accessToken=VerifyOtpTest.accessToken;

	        String randomName = TestUtils.randomName();
	        String randomEmail = TestUtils.randomEmail();

	        String requestBody = "{\n" +
	                "  \"query\": \"mutation UpdateUser($userId: String!, $name: String, $whatsappConsent: Boolean, $email: String) { " +
	                "updateUser(userId: $userId, name: $name, whatsappConsent: $whatsappConsent, email: $email) { " +
	                "success message data { userId name email whatsappConsent } errors { errorCode message statusCode } } }\",\n" +
	                "  \"variables\": {\n" +
	                "    \"userId\": \"" + userId + "\",\n" +
	                "    \"name\": \"" + randomName + "\",\n" +
	                "    \"email\": \"" + randomEmail + "\",\n" +
	                "    \"whatsappConsent\": true\n" +
	                "  }\n" +
	                "}";

	        Response response =
	                given()
	                    .log().all()
	                    .header("Authorization", "Bearer " + accessToken)
	                    .header("x-operation-name", "updateUser")
	                    .body(requestBody)
	                .when()
	                    .post("/stage/api/graphql/user")
	                .then()
	                    .log().all()
	                    .statusCode(200)
	                    .extract().response();

	        // Assertions
	        Assert.assertTrue(response.jsonPath().getBoolean("data.updateUser.success"));

	        Assert.assertEquals(
	                response.jsonPath().getString("data.updateUser.data.name"),
	                randomName
	        );

	        Assert.assertEquals(
	                response.jsonPath().getString("data.updateUser.data.email"),
	                randomEmail
	        );

	        Assert.assertTrue(
	                response.jsonPath().getBoolean("data.updateUser.data.whatsappConsent")
	        );

	        Assert.assertNull(response.jsonPath().get("data.updateUser.errors"));
	    }
	}
	
	
