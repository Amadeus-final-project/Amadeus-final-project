package com.example.pds.model.dto.vehicleDTO;

import com.example.pds.model.entity.Brand;
import com.example.pds.model.entity.Range;
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
    private Range range;
    private Brand brand;
    private Integer year;
    private String fuelType;

}
