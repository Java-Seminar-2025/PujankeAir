package com.air.pujanke.exception.exceptiontype;

import lombok.Getter;

@Getter
public class InvalidArgumentException extends RuntimeException {
    private final String redirect;

    public InvalidArgumentException(String message, String redirect) {
        super(message);
        this.redirect = redirect;
    }

    public InvalidArgumentException(String message) {
        super(message);
        this.redirect = null;
    }
}
