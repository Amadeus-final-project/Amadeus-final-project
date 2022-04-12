package com.example.pds.model.vacations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationTypeRepository extends JpaRepository<VacationType, Integer> {

    VacationType findByType(String type);
}
