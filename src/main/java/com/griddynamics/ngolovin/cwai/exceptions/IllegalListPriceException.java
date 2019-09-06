package com.griddynamics.ngolovin.cwai.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@NoArgsConstructor
public class IllegalListPriceException extends RuntimeException {

    public IllegalListPriceException(String message) {
        super(message);
    }

    public IllegalListPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalListPriceException(Throwable cause) {
        super(cause);
    }
}
