package com.example.pds.model.vehicle;

import com.example.pds.model.vehicle.vehicleProperties.Brand;
import com.example.pds.model.vehicle.vehicleProperties.Range;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private boolean isAvailable;
    @Column
    private double capacity;
    @ManyToOne
    @JoinColumn(name = "range_id", referencedColumnName = "id")
    private Range range;
    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;
    @Column
    private String year;
    @Column
    private String fuelType;
}
