package com.example.springproject.exception.base;

import static com.example.springproject.constant.ExceptionCode.NOT_FOUND_CODE;

/**
 * Custom exception class for representing Not Found scenarios.
 * Extends the BaseException class and sets the error code and status accordingly.
 */
public class NotFoundException extends BaseException {

    /**
     * Default constructor for NotFoundException.
     * Sets the error code to NOT_FOUND_CODE and the HTTP status to NOT_FOUND.
     */
    public NotFoundException() {
        setCode(NOT_FOUND_CODE);
        setStatus(StatusConstants.NOT_FOUND);
    }
}
