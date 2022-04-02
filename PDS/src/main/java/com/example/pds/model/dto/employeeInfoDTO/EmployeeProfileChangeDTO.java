package com.example.pds.model.dto.employeeInfoDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeProfileChangeDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
