package com.aweshop.products.upload.error;

import org.springframework.http.HttpStatus;

public class ProductApplicationException extends RuntimeException{

    private final HttpStatus httpStatus;

    public ProductApplicationException(String message, Throwable t, HttpStatus httpStatus) {
        super(message, t);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
