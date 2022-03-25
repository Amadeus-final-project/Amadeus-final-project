package com.example.pds.model.employees.driver.driverDTO;

import com.example.pds.model.employees.employeeInfo.EmployeeInfo;
import com.example.pds.model.offices.Office;
import com.example.pds.model.vehicle.Vehicle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Setter
@Getter
@NoArgsConstructor

public class DriverSimpleResponseDTO {
    private int id;
    private Office lastCheckedIn;
    private EmployeeInfo employeeInfo;
    private Vehicle vehicle;
}
