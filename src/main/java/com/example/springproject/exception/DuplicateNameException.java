package com.example.springproject.exception;

import com.example.springproject.exception.base.ConflictException;

import static com.example.springproject.constant.ExceptionCode.DUPLICATE_CODE;

/**
 * Custom exception class for representing a Duplicate Name scenario.
 * Extends the ConflictException class and sets the error code accordingly.
 */
public class DuplicateNameException extends ConflictException {

    /**
     * Default constructor for DuplicateNameException.
     * Sets the error code to DUPLICATE_CODE, inheriting the conflict status from its superclass.
     */
    public DuplicateNameException() {
        setCode(DUPLICATE_CODE);
    }
}
