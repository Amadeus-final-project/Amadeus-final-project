package com.example.pds.model.vehicle.vehicleProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Table(name = "brands")
@Getter
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String brandName;

    @Override
    public String toString() {
        return brandName;
    }

}
