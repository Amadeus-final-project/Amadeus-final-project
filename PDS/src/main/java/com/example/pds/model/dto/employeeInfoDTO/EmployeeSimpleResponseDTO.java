package com.example.pds.model.dto.employeeInfoDTO;

import com.example.pds.model.entity.EmployeeInfo;
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