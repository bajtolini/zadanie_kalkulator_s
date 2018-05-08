package pl.salary.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = "pl.salary.calculator")
@EnableAutoConfiguration
@SpringBootApplication
public class SalaryCalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(SalaryCalculatorApplication.class, args);
    }
}
