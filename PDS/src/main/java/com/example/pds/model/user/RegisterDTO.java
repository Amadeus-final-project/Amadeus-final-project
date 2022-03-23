package com.example.pds.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
public class RegisterDTO {
    private int id;

    @Length(min = 5, message = "Username should be at least 5 symbols")
    private String username;

    @NotBlank(message = "First name is mandatory")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]+", message = "First name should be only letters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]+", message = "Last name should be only letters")
    private String lastName;

    @Length(min = 8, message = "Password must be at least 8 symbols")
    private String password;

    private String confirmPassword;

    @Email(message = "Invalid email address")
    private String email;

}
