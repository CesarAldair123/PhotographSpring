package com.example.Photograph.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {
    @NotBlank(message = "Username must be not empty")
    private String username;
    @Email(message = "Email must be valid")
    private String email;
    @NotBlank(message = "Password must be not empty")
    private String password;
    @NotNull(message = "Birthdate must be not empty")
    private LocalDate birthdate;
}
