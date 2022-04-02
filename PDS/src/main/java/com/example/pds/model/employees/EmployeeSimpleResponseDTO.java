package com.example.pds.model.employees;

import com.example.pds.model.employees.employeeInfo.EmployeeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EmployeeSimpleResponseDTO {
    private int id;
    private EmployeeInfo employeeInfo;
}
