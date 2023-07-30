package com.restaurant.app.exceptions;

public class UserUnauthorizedException extends RuntimeException {

    public UserUnauthorizedException() {
        super();
    }

    public UserUnauthorizedException(String message) {
        super(message);
    }
}