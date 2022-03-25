package com.example.pds.model.employees.driver.driverDTO;

import com.example.pds.model.employees.employeeInfo.EmployeeInfo;
import com.example.pds.model.offices.Office;
import com.example.pds.model.vehicle.Vehicle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class DriverRegisterDTO {

    private Integer id;
    private String email;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Boolean isWorking;
    private Integer remainingPaidLeave;


}