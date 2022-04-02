package com.example.pds.model.employees.employeeInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeInfo,Integer> {

    EmployeeInfo findById(int id);
}
