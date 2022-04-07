package com.example.pds.model.packages;

import com.example.pds.model.address.Address;
import com.example.pds.model.employees.driver.DriverProfile;
import com.example.pds.model.offices.Office;
import com.example.pds.model.transaction.Transaction;
import com.example.pds.model.user.UserProfile;
import com.example.pds.profiles.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "packages")
@Setter
@Getter
@NoArgsConstructor
public class Package {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private UserProfile sender;
    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private UserProfile recipient;
    @ManyToOne
    @JoinColumn(name = "delivery_address_id", referencedColumnName = "id")
    private Address address;
    @ManyToOne
    @JoinColumn(name = "assigned_driver_id", referencedColumnName = "id")
    private DriverProfile driver;
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;
    @ManyToOne
    @JoinColumn(name = "current_location_office_id", referencedColumnName = "id")
    private Office office;
    @OneToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private Transaction transaction;
    @ManyToOne
    @JoinColumn(name = "delivery_type_id", referencedColumnName = "id")
    private DeliveryType deliveryType;
    @Column
    private Boolean isSigned;
    //@ManyToOne
    // @JoinColumn(name = "package_dimensions_id", referencedColumnName = "id")
    // private PackageDimensions packageDimensions;
    @Column
    private Double volume;
    @Column
    private Double weight;
    @Column
    private Boolean isFragile;
    @Column
    private String trackingNumber;
    @Column
    private LocalDate dueDate;
    @Column
    private String description;

}
