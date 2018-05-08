package pl.salary.calculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRateResponseException extends RuntimeException {
    public InvalidRateResponseException(String msg) {
        super(msg);
    }
}