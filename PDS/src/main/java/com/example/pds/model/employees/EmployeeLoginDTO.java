package com.example.pds.model.employees;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class EmployeeLoginDTO {

    @NotNull(message = "Please enter username")
    private String email;

    @NotNull(message = "Please enter password")
    private String password;
}
