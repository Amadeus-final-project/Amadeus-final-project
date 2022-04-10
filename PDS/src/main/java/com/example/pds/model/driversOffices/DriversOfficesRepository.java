package com.example.pds.model.driversOffices;

import com.example.pds.model.employees.driver.DriverProfile;
import com.example.pds.model.offices.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

@Repository
public interface DriversOfficesRepository extends JpaRepository<DriversOffices,Integer> {
    DriversOffices findByDriverAndOffice(DriverProfile driverProfile, Office office);

    List<DriversOffices> findAllByDriver(DriverProfile driverProfile);
}
