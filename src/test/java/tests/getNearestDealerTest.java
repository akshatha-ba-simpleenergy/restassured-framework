package tests;

import Base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class getNearestDealerTest extends BaseTest {
	
	public static String facilityId;

    @Test(priority = 6)
    public void getNearestDealer_Success() {
    	
    

        String requestBody = "{\n" +
                "  \"query\": \"query GetNearestDealer($types: [FacilityType!], $page: Int, $limit: Int, $pincode: String) { " +
                "getNearestDealer(types: $types, page: $page, limit: $limit, pincode: $pincode) { " +
                "success message statusCode currentPage limit totalPages totalCount " +
                "dealers { id name email phone_number authorised_dealer serial_number status " +
                "facilities { id facility_code type location city state pincode latitude longitude } } } }\",\n" +
                "  \"variables\": {\n" +
                "    \"pincode\": \"560064\",\n" +
                "    \"types\": [\"SHOWROOM\"],\n" +
                "    \"page\": 1,\n" +
                "    \"limit\": 4\n" +
                "  }\n" +
                "}";

        Response response =
                given()
                        .log().all()
                        .baseUri(BaseTest.dmsUrl)
                        .header("x-api-key" , "eyJhbGciOiasdasdasdasdnR5cCI6IkpXVCJ9.eyJpZCI6Ij")
                        .header("x-operation-name", "getNearestDealer")
                        .body(requestBody)
                .when()
                        .post("/api/graphql")
                .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response();
        
        facilityId = response.jsonPath()
                .getString("data.getNearestDealer.dealers[0].facilities[0].id");

        // Assertions
        Assert.assertTrue(
                response.jsonPath().getBoolean("data.getNearestDealer.success")
        );

        Assert.assertNotNull(
                response.jsonPath().getList("data.getNearestDealer.dealers")
        );

        Assert.assertEquals(
                response.jsonPath().getInt("data.getNearestDealer.currentPage"),
                1
        );

        Assert.assertEquals(
                response.jsonPath().getInt("data.getNearestDealer.limit"),
                4
        );
    }
}