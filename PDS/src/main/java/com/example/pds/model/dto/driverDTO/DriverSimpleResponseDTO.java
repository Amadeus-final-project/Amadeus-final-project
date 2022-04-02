package com.example.pds.model.dto.driverDTO;

import com.example.pds.model.entity.EmployeeInfo;
import com.example.pds.model.entity.Office;
import com.example.pds.model.entity.Vehicle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor

public class DriverSimpleResponseDTO {
    private int id;
    private Office lastCheckedIn;
    private EmployeeInfo employeeInfo;
    private Vehicle vehicle;
}
