package com.fpt.MidtermG1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InactiveCustomerException extends RuntimeException {
    public InactiveCustomerException(String message) {
        super(message);
    }
}
