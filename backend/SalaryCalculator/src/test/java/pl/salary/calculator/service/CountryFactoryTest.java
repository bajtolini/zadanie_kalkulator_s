package pl.salary.calculator.service;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
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

import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CountryFactoryTest {

    @InjectMocks
    CountryFactory countryFactory;

    @Mock
    RateProvider rp;

    @Before
    public void before() {
        rp = mock(RateProvider.class);
        countryFactory = new CountryFactory(rp);
        when(rp.getRateForCurrency(ArgumentMatchers.any(SupportedCurrencies.class))).thenReturn(0d);
    }

    @ParameterizedTest(name = "For country {0}, result should be {1}")
    @MethodSource("monthlyNetIncomeParams")
    @DisplayName("Test getting monthly net income")
    public void testMonthlyNetIncome(SupportedCountries country, Country expected) {
        Country result = countryFactory.getCountry(country);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> monthlyNetIncomeParams() {
        return Stream.of(
                //pl rate is 1 because it is not using mocked rateprovider
                Arguments.of(SupportedCountries.PL, Country.builder().taxes(19.0).rateToPLN(1d).fixedExpanses(1200.0).build()),
                Arguments.of(SupportedCountries.UK, Country.builder().taxes(25.0).rateToPLN(0d).fixedExpanses(600.0).build()),
                Arguments.of(SupportedCountries.DE, Country.builder().taxes(20.0).rateToPLN(0d).fixedExpanses(800.0).build())
        );
    }
}
