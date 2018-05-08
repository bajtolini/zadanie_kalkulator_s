package pl.salary.calculator.tools.client;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.ValidatableResponse;
import pl.salary.calculator.model.SupportedCountries;

import static com.jayway.restassured.RestAssured.given;

public final class SalaryCalculatorTestClient {

    private static final String SUPPORTED_COUNTRIES_PATH = "/supportedCountries";
    private static final String CALCULATE_SALARY_PATH = "/calculate";

    public static ValidatableResponse getSupportedCountries() {
        return given().contentType(ContentType.JSON)
                .with()
                .when()
                .get(SUPPORTED_COUNTRIES_PATH)
                .then()
                .log()
                .ifError();
    }

    public static ValidatableResponse calculateSalary(Double amount, SupportedCountries country) {
        return given().contentType(ContentType.JSON)
                .with()
                .when()
                .pathParam("amount", amount)
                .pathParam("country", country)
                .get(CALCULATE_SALARY_PATH + "/{amount}/{country}")
                .then()
                .log()
                .ifError();
    }

    private SalaryCalculatorTestClient() {
    }
}
