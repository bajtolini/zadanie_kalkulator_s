package pl.salary.calculator.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.salary.calculator.exception.UnsupportedCountryException;
import pl.salary.calculator.model.Country;
import pl.salary.calculator.model.SupportedCountries;
import pl.salary.calculator.model.SupportedCurrencies;
import pl.salary.calculator.service.webTarget.RateProvider;

@Log
@Service
public class CountryFactory {

    private RateProvider rateProvider;

    @Autowired
    public CountryFactory(RateProvider rateProvider) {
        this.rateProvider = rateProvider;
    }

    public Country getCountry(SupportedCountries supportedCountries) {
        log.info("Getting Country for enum " + supportedCountries);
        switch (supportedCountries) {
            case DE: return getDECountry();
            case UK: return getUKCountry();
            case PL: return getDefaultCountry();
            default: throw new UnsupportedCountryException(supportedCountries.toString());
        }
    }

    private Country getDefaultCountry() {
        log.info("Getting Country for default");
        return Country.builder()
                .taxes(19.0)
                .fixedExpanses(1200.0)
                .rateToPLN(1.0)
                .build();
    }

    private Country getUKCountry() {
        log.info("Getting Country for UK");
        return Country.builder()
                .taxes(25.0)
                .fixedExpanses(600.0)
                .rateToPLN(rateProvider.getRateForCurrency(SupportedCurrencies.GBP))
                .build();
    }

    private Country getDECountry() {
        log.info("Getting Country for DE");
        return Country.builder()
                .taxes(20.0)
                .fixedExpanses(800.0)
                .rateToPLN(rateProvider.getRateForCurrency(SupportedCurrencies.EUR))
                .build();
    }
}