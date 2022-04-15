package com.example.pds.model.employees.driver;

import com.example.pds.model.employees.agent.AgentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<DriverProfile, Integer> {

    DriverProfile findById(int id);

    DriverProfile findByProfileId(int id);

    DriverProfile getByProfileId(int id);
}
