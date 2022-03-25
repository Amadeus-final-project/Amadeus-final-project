package com.example.pds.model.vehicle;

import com.example.pds.model.vehicle.vehicleProperties.Brand;
import com.example.pds.model.vehicle.vehicleProperties.Range;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
public class VehicleComplexDTO {
    private int id;
    private Boolean isAvailable;
    private Double capacity;
    private Range range;
    private Brand brand;
    private Integer year;
    private String fuelType;

}
