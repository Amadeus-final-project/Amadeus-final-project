package com.example.pds.model.employees.driver;

import com.example.pds.controllers.profiles.Profile;
import com.example.pds.model.employees.agent.AgentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Driver;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<DriverProfile, Integer> {

    DriverProfile findById(int id);

    DriverProfile findByProfileId(int id);

    DriverProfile getByProfileId(int id);

    Optional<DriverProfile> findByProfile(Profile profile);
}
