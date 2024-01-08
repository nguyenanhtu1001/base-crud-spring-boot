package com.example.springproject.exception.base;

import static com.example.springproject.constant.ExceptionCode.BAD_REQUEST_CODE;

/**
 * Custom exception class for representing Bad Request scenarios.
 * Extends the BaseException class and sets the error code and status accordingly.
 */
public class BadRequestException extends BaseException {

    /**
     * Default constructor for BadRequestException.
     * Sets the error code to BAD_REQUEST_CODE and the HTTP status to BAD_REQUEST.
     */
    public BadRequestException() {
        setCode(BAD_REQUEST_CODE);
        setStatus(StatusConstants.BAD_REQUEST);
    }
}