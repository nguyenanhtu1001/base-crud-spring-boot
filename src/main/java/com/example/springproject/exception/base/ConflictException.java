package com.example.springproject.exception.base;


import static com.example.springproject.constant.ExceptionCode.CONFLICT_CODE;
import static com.example.springproject.exception.base.StatusConstants.CONFLICT;

/**
 * Custom exception class for representing Conflict scenarios.
 * Extends the BaseException class and sets the error code, status, and additional parameters accordingly.
 */
public class ConflictException extends BaseException {

    /**
     * Constructor for ConflictException with specific parameters.
     *
     * @param id         The identifier related to the conflict.
     * @param objectName The name of the object causing the conflict.
     */
    public ConflictException(String id, String objectName) {
        setStatus(CONFLICT);
        setCode(CONFLICT_CODE);
        addParam("id", id);
        addParam("objectName", objectName);
    }

    /**
     * Default constructor for ConflictException.
     * Sets the error code and HTTP status to represent a conflict.
     */
    public ConflictException() {
        setStatus(CONFLICT);
        setCode(CONFLICT_CODE);
    }
}

