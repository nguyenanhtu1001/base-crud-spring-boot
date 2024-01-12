package com.example.springproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a response dto (data transfer object) class contains user information
 *
 * @author [nguyenanhtu123]
 * @version [1.0.0]
 * @since 1/6/2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
  private String id;
  private String username;
  private String password;
  private String email;
  private String phone;
  private String role;

  public UserResponse(String username, String password, String email, String phone, String role) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.phone = phone;
    this.role = role;
  }
}