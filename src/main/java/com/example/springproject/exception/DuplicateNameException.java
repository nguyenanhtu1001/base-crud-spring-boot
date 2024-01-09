package com.example.springproject.exception;

import com.example.springproject.exception.base.ConflictException;

import static com.example.springproject.constant.ExceptionCode.DUPLICATE_CODE;

/**
 * DuplicateNameException is a type of exception commonly
 * used to indicate that there is a duplicate in the name or identifier of an object or resource in the system.
 */
public class DuplicateNameException extends ConflictException {
  public DuplicateNameException(){
    setCode(DUPLICATE_CODE);
  }
}
