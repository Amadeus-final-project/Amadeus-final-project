package com.example.pds.model.employees.driver;


import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String phoneNumber;
    @Column
    private String first_name;
    @Column
    private String last_name;
    @Column
    private Boolean isWorking;
    @Column
    private int remainingPaidLeave;
}
