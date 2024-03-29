package com.example.springproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a request dto (data transfer object) class contains user information
 * @author [nguyenanhtu123]
 * @version [1.0.0]
 * @since 1/6/2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
  private String username;
  private String password;
  private String email;
  private String phone;
  private String role;
}