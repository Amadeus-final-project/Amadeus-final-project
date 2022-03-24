package com.example.pds.model.employees;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
@NoArgsConstructor
public class EmployeeSimpleResponseDTO {
    private int id;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Boolean isWorking;
}
