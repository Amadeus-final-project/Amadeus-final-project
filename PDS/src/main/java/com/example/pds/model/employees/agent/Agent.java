package com.example.pds.model.employees.agent;

import com.example.pds.model.employees.employeeInfo.EmployeeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "agents")
@Getter
@Setter
@NoArgsConstructor
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "employee_info_id", referencedColumnName = "id")
    private EmployeeInfo employeeInfo;

    @Column
    private String email;
    @Column
    private String password;

}