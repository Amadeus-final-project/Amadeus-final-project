package com.example.pds.model.vehicle;

import com.example.pds.model.vehicle.vehicleProperties.Brand;
import com.example.pds.model.vehicle.vehicleProperties.Range;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class VehicleComplexDTO {
    private int id;
    private Boolean isAvailable;
    private Double capacity;
    private String range;
    private String brand;
    private Integer year;
    private String fuelType;

}
