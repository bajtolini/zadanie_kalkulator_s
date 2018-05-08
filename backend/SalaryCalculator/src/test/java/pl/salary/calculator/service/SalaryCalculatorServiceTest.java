package pl.salary.calculator.service;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.salary.calculator.model.Country;
import pl.salary.calculator.model.SupportedCountries;
import pl.salary.calculator.model.SupportedCurrencies;
import pl.salary.calculator.service.webTarget.RateProvider;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SalaryCalculatorServiceTest {

    @Autowired
    SalaryCalculatorService salaryCalculatorService;

    @ParameterizedTest(name = "For daily gross {0}, taxes {1}, expanses {2} result should be {3}")
    @MethodSource("monthlyNetIncomeParams")
    @DisplayName("Test getting monthly net income")
    public void testMonthlyNetIncome(Double amount, Double taxes, Double fixedExpanses, Double result) {
        assertEquals(result, salaryCalculatorService.getMonthlyNetIncome(amount, taxes, fixedExpanses));
    }

    private static Stream<Arguments> monthlyNetIncomeParams() {
        return Stream.of(
                //preferred case
                Arguments.of(1000.0, 20.0, 100.0, 17500.0),
                //expanses cases
                Arguments.of(1000.0, 20.0, 0.0, 17600.0),
                Arguments.of(1000.0, 20.0, 20000.0, -2400.0),
                //taxes cases
                Arguments.of(1000.0, 0.0, 100.0, 21900.0),
                Arguments.of(1000.0, 100.0, 100.0, -100.0),
                //daily gross cases
                Arguments.of(0.0, 20.0, 100.0, -100.0),
                Arguments.of(-1000.0, 20.0, 100.0, -17700.0)
        );
    }

    @ParameterizedTest(name = "For daily gross {0}, and country {1} result should be {2}")
    @MethodSource("monthlySalaryParams")
    @DisplayName("Test getting monthly salary")
    public void testMonthlySalary(Double amount, SupportedCountries country, BigDecimal result) {
        assertEquals(result, salaryCalculatorService.calculateMonthlySalary(amount, country));
    }

    private static Stream<Arguments> monthlySalaryParams() {
        return Stream.of(
                //preferred case
                Arguments.of(1000.0, SupportedCountries.PL, BigDecimal.valueOf(16620.00).setScale(2)),
                Arguments.of(2000.0, SupportedCountries.DE, BigDecimal.valueOf(146757.28)),
                Arguments.of(0.0, SupportedCountries.UK, BigDecimal.valueOf(-2908.26)),

                Arguments.of(-1000.0, SupportedCountries.DE, BigDecimal.valueOf(-78498.08))
        );
    }

    @DisplayName("Test getting all supported countries enum")
    @Test
    public void testGetSupportedCountries() {
        List<SupportedCountries> countries = salaryCalculatorService.getSupportedCountries();

        assertThat(countries, hasSize(SupportedCountries.values().length));
        assertThat(countries, containsInAnyOrder(SupportedCountries.values()));
    }


}