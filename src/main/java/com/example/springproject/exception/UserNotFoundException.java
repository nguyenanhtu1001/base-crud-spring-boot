package com.example.springproject.exception;

import com.example.springproject.exception.base.NotFoundException;

import static com.example.springproject.constant.ExceptionCode.USER_NOT_FOUND_CODE;

/**
 * Custom exception class for representing a User Not Found scenario.
 * Extends the NotFoundException class and sets the error code accordingly.
 */
public class UserNotFoundException extends NotFoundException {

    /**
     * Default constructor for UserNotFoundException.
     * Sets the error code to USER_NOT_FOUND_CODE, inheriting the not found status from its superclass.
     */
    public UserNotFoundException() {
        setCode(USER_NOT_FOUND_CODE);
    }
}