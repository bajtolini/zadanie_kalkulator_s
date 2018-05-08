package pl.salary.calculator.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.salary.calculator.model.Country;
import pl.salary.calculator.model.SupportedCountries;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

@Log
@Service
public class SalaryCalculatorService {

    @Value("${month.days}")
    private Integer daysInMonth = 10;

    private CountryFactory countryFactory;

    @Autowired
    public SalaryCalculatorService(CountryFactory countryFactory) {
        this.countryFactory = countryFactory;
    }

    public BigDecimal calculateMonthlySalary(Double amount, SupportedCountries supportedCountry) {
        Country country = countryFactory.getCountry(supportedCountry);

        return BigDecimal.valueOf(getMonthlyNetIncome(amount, country.getTaxes(), country.getFixedExpanses()) * country.getRateToPLN()).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public Double getMonthlyNetIncome(Double amount, Double taxes, Double fixedExpanses) {
        Double monthlyGross = amount * daysInMonth;
        Double percentageOfNetIncome = (100 - taxes) / 100;

        return (monthlyGross * percentageOfNetIncome) - fixedExpanses;
    }

    public List<SupportedCountries> getSupportedCountries() {
        return Arrays.asList(SupportedCountries.values());
    }
}