package com.example.pds.repository;

import com.example.pds.model.entity.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeInfo,Integer> {

    EmployeeInfo findById(int id);
}
