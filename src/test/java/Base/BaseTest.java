package Base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    public static String dmsUrl;
    public static String paymentUrl;
    

    @BeforeClass
    public void setup() {

        String baseUrl = ConfigReader.get("base.url");
        

        dmsUrl = ConfigReader.get("dms.url");
        paymentUrl=ConfigReader.get("payment.url");
        

        if (baseUrl == null) {
            throw new RuntimeException("base.url is missing in config.properties");
        }

        RestAssured.baseURI = baseUrl;
       

        RestAssured.requestSpecification =
                RestAssured.given()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json");
    }
}