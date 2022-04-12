package com.example.pds.model.employees.driver.driverDTO;

import com.example.pds.model.offices.Office;
import com.example.pds.model.vehicle.Vehicle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DriverSimpleResponseDTO {
    private int id;
    private String lastCheckedIn;
    private String vehicle;
    private String firstName;
    private String driverStatus;
}
