package com.example.springproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {

    /**
     * The unique username associated with the user.
     */
    @NotBlank
    @NotNull
    @Size(min = 6, max = 15, message = "Username must be over 6 characters long and under 15 characters")
    private String username;

    /**
     * The encoded string representation associated with the user.
     * This string must follow the regex rule, at least 1 special character, 1 number, 1 Uppercase character
     * and have at least 6 characters long
     */

    @Pattern(regexp = "^(?=.*[!@#$%^&*()-_+=])(?=.*[A-Z])(?=.*[0-9]).{6,}$", message = "Password must contains at least 1 uppercase character, 1 number, 1 special character and have at least 6 characters!")
    private String password;

    private String email;
    private String phone;
    private String role;
}
