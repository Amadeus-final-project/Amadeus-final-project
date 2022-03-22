package com.example.pds.model.employees.driver;

import com.example.pds.model.address.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
public class Driver extends BaseEmployee{
   // @OneToOne
    //@JoinColumn(name = "vehicle_id", referencedColumnName = "id")
   // private Vehicle vehicle;
    @OneToOne
    @JoinColumn(name="last_checked_in_office_id", referencedColumnName = "id")
    private Address address;
}
