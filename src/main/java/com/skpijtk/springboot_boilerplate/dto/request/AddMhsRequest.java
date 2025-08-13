package com.skpijtk.springboot_boilerplate.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMhsRequest {
    @NotBlank(message = "Student name cannot be blank")
    @Size(max = 50, message = "Student name must be at most 50 characters")
    @Pattern(
        regexp = "^(?!\\s)([A-Za-z]{1,})(\\s[A-Za-z]{1,})$",
        message = "Student name must consist of first and last name, letters only, no trailing spaces or special characters"
    )
    private String studentName;

    @NotBlank(message = "NIM cannot be blank")
    @Size(min = 9, max = 9, message = "NIM must be exactly 9 digits")
    @Pattern(
        regexp = "^\\d{9}$",
        message = "NIM must be 9 digits with numbers only and no spaces or special characters"
    )
    private String nim;

    @NotBlank(message = "Email cannot be blank")
    @Size(min = 1, max = 50, message = "Email must be between 1 and 50 characters")
    @Email(message = "Email format is invalid")
    @Pattern(
        regexp = "^[^\\s]+@[^\\s]+\\.(com|[a-z]{2,})$",
        message = "Email must be valid and not contain spaces"
    )
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 12, message = "Password must be between 6 and 12 characters")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,12}$",
        message = "Password must contain letters and numbers only, no spaces or special characters"
    )
    private String password;
}
