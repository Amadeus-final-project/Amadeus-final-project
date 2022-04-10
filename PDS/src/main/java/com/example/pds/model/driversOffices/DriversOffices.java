package com.example.pds.model.driversOffices;

import com.example.pds.model.employees.driver.DriverProfile;
import com.example.pds.model.offices.Office;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name ="drivers_offices")
public class DriversOffices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private DriverProfile driver;
    @ManyToOne
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    private Office office;
}
