package com.example.pds.model.employees.driver;

import com.example.pds.model.address.Address;
import com.example.pds.model.employees.employeeStatus.EmployeeStatus;
import com.example.pds.model.offices.Office;
import com.example.pds.model.vehicle.Vehicle;
import com.example.pds.controllers.profiles.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "drivers_profile")
@Getter
@Setter
@NoArgsConstructor
public class DriverProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "driver_status_id", referencedColumnName = "id")
    private EmployeeStatus driverStatus;

    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "last_checked_in", referencedColumnName = "id")
    private Office lastCheckedIn;

    @OneToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "working_address_id", referencedColumnName = "id")
    private Address workingAddress;

    @Column(name = "available_paid_leave")
    private Integer availablePaidLeave;



}
