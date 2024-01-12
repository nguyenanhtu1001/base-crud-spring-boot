package com.example.springproject.dto.response;

import com.example.springproject.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This is a response dto (data transfer object) class contains user information
 * @author [nguyenanhtu123]
 * @version [1.0.0]
 * @since 1/6/2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
  private String id;
  private String username;
  private String password;
  private String email;
  private String phone;
  private Role role;
}