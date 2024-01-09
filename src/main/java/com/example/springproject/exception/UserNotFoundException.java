package com.example.springproject.exception;

import com.example.springproject.exception.base.NotFoundException;
import static com.example.springproject.constant.ExceptionCode.USER_NOT_FOUND_CODE;

/**
 * UserNotFoundException is a type of exception commonly
 * used to indicate that the user cannot be found
 */
public class UserNotFoundException extends NotFoundException {
  public UserNotFoundException() {
    setCode(USER_NOT_FOUND_CODE);
  }
}