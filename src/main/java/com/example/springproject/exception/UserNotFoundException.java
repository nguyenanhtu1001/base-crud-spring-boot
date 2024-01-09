package com.example.springproject.exception;

import com.example.springproject.exception.base.NotFoundException;

/**
 * UserNotFoundException is a type of exception commonly
 * used to indicate that the user cannot be found
 */
public class UserNotFoundException extends NotFoundException {
  public UserNotFoundException() {
    setCode("com.example.springproject.exception.base.NotFoundException.UserNotFoundException");
  }
}