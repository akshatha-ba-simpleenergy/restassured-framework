package tests;

import Base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class ListAllVehicleVariantsTest extends BaseTest {
	
	public static String variantId;

    @Test(priority = 4)
    public void listAllVehicleVariants_Success() {
    	
    	 String accessToken=VerifyOtpTest.accessToken;
    	int page = 1;
    	int offset = 100;

        String requestBody = "{\n" +
                "  \"query\": \"query ListAllVehicleVariants($page: Int, $offset: Int, $search: String) { " +
                "listAllVehicleVariants(page: $page, offset: $offset, search: $search) { " +
                "success message data { id name model_id model { name description gen active } created_date active } " +
                "errors { message status success keyword } } }\",\n" +
                "  \"variables\": {\n" +
                "    \"page\": "+ page + ",\n" +
                "    \"offset\": "+ offset +  "\n" +
                "  }\n" +
                "}";

        Response response =
                given()
                    .log().all()
                    .header("Authorization", "Bearer " + accessToken)
                    .header("x-api-key" ,"bGV0bWVsaXZlaW5wZWFjZQ==")
                    .header("x-operation-name" ,"listAllVehicleVariants")
                    .body(requestBody)
                .when()
                    .post("/stage/api/graphql/order")
                .then()
                    .log().all()
                    .statusCode(200)
                    .extract().response();
        
        variantId = response.jsonPath()
                .getString("data.listAllVehicleVariants.data[0].id");

        // Assertions
        Assert.assertTrue(
                response.jsonPath().getBoolean("data.listAllVehicleVariants.success")
        );

        Assert.assertEquals(
                response.jsonPath().getString("data.listAllVehicleVariants.message"),
                "Vehicle models fetched successfully"
        );

        Assert.assertNotNull(
                response.jsonPath().getList("data.listAllVehicleVariants.data")
        );

        Assert.assertNull(
                response.jsonPath().get("data.listAllVehicleVariants.errors")
        );
    }
}