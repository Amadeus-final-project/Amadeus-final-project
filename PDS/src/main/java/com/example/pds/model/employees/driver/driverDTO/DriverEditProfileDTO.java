package com.example.pds.model.employees.driver.driverDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
@Setter
@Getter
@NoArgsConstructor
public class DriverEditProfileDTO {
    private int id;

    @NotBlank(message = "First name is mandatory")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]+", message = "First name should be only letters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]+", message = "Last name should be only letters")
    private String lastName;
    @Email(message = "Invalid email address")
    private String email;

    @Pattern(regexp = "^\\d{10}$", message = "Invalid phone number")
    private String phoneNumber;
}
