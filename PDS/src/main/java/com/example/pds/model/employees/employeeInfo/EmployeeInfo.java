package com.example.pds.model.employees.employeeInfo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "employee_info")

public  class EmployeeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String phoneNumber;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private Boolean isWorking;
    @Column
    private int remainingPaidLeave;
}
