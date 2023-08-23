package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class FunCatFacts {

	public static String getFunFact() {
		RestAssured.baseURI = "https://catfact.ninja";
		// Send a GET request
		Response response = RestAssured.given().header("accept", "application/json").when().get("/fact");
		FunFactResponse funFactRes = response.as(FunFactResponse.class);
		return funFactRes.getFact();

	}

}
