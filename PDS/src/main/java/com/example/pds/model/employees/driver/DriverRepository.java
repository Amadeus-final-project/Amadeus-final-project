package com.example.pds.model.employees.driver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    Driver findByEmail(String email);

    Driver findById(int id);
}