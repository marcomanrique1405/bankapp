package com.marco.bankapp.auth.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Size(max = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    private String lastName;

    @NotBlank
    @Email
    @Size(max = 320)
    @Pattern(
            regexp = "^[^A-Z]*$",
            message = "Email must be lowercase"
    )
    private String email;

    @NotBlank
    private String password;
    
    @Size(max = 20)
    private String phone;

    @NotNull
    private LocalDate dateOfBirth;

}
