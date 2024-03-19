package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    @NotEmpty(message = "user id should not be empty")
    private String id;
    @NotEmpty(message = "username should not be empty")
    @Size(min = 6,message = "username should be at least 6 characters")
    private String username;
    @NotEmpty(message = "password should not be empty")
    @Size(min = 7,message = "password should be at least 7 characters")
    @Pattern(regexp = "^((?=\\S*?[a-zA-Z])(?=\\S*?[0-9]).{7,})\\S$",message = "password must contain digit and characters")
    private String password;
    @NotEmpty(message = "email should not be empty")
    @Email(message = "enter a valid email")
    private String email;
    @NotEmpty(message = "role should not be empty")
    @Pattern(regexp = "^(Admin|Customer)$",message = "role should be either Admin or Customer")
    private String role;
    @NotNull(message = "balance should not be empty")
    @PositiveOrZero(message = "balance can not be a negative number")
    private Double balance;
}
