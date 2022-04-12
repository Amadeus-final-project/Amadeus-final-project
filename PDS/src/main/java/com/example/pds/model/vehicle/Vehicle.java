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
@Table(name = "vehicles")
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private Boolean isAvailable;
    @Column
    private Double capacity;
    @ManyToOne
    @JoinColumn(name = "range_id", referencedColumnName = "id")
    private Range range;
    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;
    @Column
    private Integer year;
    @Column
    private String fuelType;


    @Override
    public String toString() {
        return brand.toString();
    }
}
