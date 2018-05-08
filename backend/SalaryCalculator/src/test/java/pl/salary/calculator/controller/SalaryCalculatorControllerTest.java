package pl.salary.calculator.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import pl.salary.calculator.SalaryCalculatorApplication;
import pl.salary.calculator.config.UrlFinder;
import pl.salary.calculator.model.SupportedCountries;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;
import static pl.salary.calculator.tools.client.SalaryCalculatorTestClient.*;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SalaryCalculatorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SalaryCalculatorControllerTest {

    @Autowired
    UrlFinder urlFinder;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void before() {
        RestAssured.baseURI = urlFinder.findApplicationUrl();
    }

    @Test
    public void shouldGetSupportedCountries() throws IOException {
        List<SupportedCountries> expected = Arrays.asList(SupportedCountries.values());

        JsonPath jsonInput = getSupportedCountries().statusCode(200).extract().jsonPath();
        List<SupportedCountries> result = objectMapper.readValue(jsonInput.prettify(), new TypeReference<List<SupportedCountries>>() {});

        assertThat(expected, is(result));
    }

    @Test
    public void shouldGetMonthlySalary() {
        Double dailyAmount = 100.0;
        SupportedCountries country = SupportedCountries.UK;

        BigDecimal result = calculateSalary(dailyAmount, country).statusCode(200).extract().body().as(BigDecimal.class);
        assertThat(result, greaterThan(BigDecimal.valueOf(0)));
    }

    @Test
    public void shouldGetMonthlySalaryForDefaultCountry() {
        Double dailyAmount = 100.0;
        SupportedCountries country = SupportedCountries.PL;

        BigDecimal result = calculateSalary(dailyAmount, country).statusCode(200).extract().body().as(BigDecimal.class);
        assertThat(result, greaterThan(BigDecimal.valueOf(0)));
    }

}