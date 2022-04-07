package com.example.pds.model.employees.agent;

import com.example.pds.model.user.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<AgentProfile, Integer> {

    AgentProfile findById(int id);

    AgentProfile findByProfileId(int id);
}
