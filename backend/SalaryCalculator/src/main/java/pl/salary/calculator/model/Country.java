package pl.salary.calculator.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Country {
    private Double taxes;
    private Double fixedExpanses;
    private Double rateToPLN;
}