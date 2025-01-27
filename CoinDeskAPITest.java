import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CoinDeskAPITest {

    @Test
    public void testGetBitcoinPriceIndex() {
        // Base URI of the API
        RestAssured.baseURI = "https://api.coindesk.com/v1/bpi/currentprice.json";

        // Send GET request and capture the response
        Response response = given()
                .when()
                .get()
                .then()
                .statusCode(200) // Verify HTTP status code is 200
                .extract()
                .response();

        // Parse and validate response
        String responseBody = response.getBody().asString();

        // Verify there are 3 BPIs: USD, GBP, EUR
        assertThat(responseBody, allOf(containsString("\"USD\""), containsString("\"GBP\""), containsString("\"EUR\"")));

        // Verify GBP description equals "British Pound Sterling"
        String gbpDescription = response.jsonPath().getString("bpi.GBP.description");
        assertThat(gbpDescription, equalTo("British Pound Sterling"));
    }
}

