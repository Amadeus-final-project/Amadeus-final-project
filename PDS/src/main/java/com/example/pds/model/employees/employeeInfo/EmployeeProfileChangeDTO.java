package com.example.pds.model.employees.employeeInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@NoArgsConstructor
public class EmployeeProfileChangeDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
