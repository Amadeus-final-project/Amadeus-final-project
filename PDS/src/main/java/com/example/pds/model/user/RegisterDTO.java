package com.example.pds.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
public class RegisterDTO {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    private String email;

}
