package pl.salary.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.salary.calculator.model.SupportedCountries;
import pl.salary.calculator.service.SalaryCalculatorService;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin
@RestController
public class SalaryCalculatorController {

    private SalaryCalculatorService salaryCalculatorService;

    @Autowired
    public SalaryCalculatorController(SalaryCalculatorService salaryCalculatorService) {
        this.salaryCalculatorService = salaryCalculatorService;
    }

    @GetMapping("/supportedCountries")
    public List<SupportedCountries> getSupportedCountries() {
        return salaryCalculatorService.getSupportedCountries();
    }

    @GetMapping("/calculate/{amount}/{country}")
    public BigDecimal calculateMonthlySalary(@PathVariable("amount") Double amount, @PathVariable("country") SupportedCountries supportedCountry) {
        return salaryCalculatorService.calculateMonthlySalary(amount, supportedCountry);
    }

}