package com.example.pds.model.employees.admin;

import com.example.pds.model.employees.employeeInfo.EmployeeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "admins")
@Setter
@Getter
@NoArgsConstructor

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "employee_info_id", referencedColumnName = "id")
    private EmployeeInfo employeeInfo;
    @Column
    private String email;
    @Column
    private String password;
}
