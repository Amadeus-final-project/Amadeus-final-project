package com.example.pds.model.employees.agent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Integer> {
    Agent findByEmail(String email);

    Agent findById(int id);
}
