package com.example.pds.model.packages.packageDTO;

import com.example.pds.model.address.Address;
import com.example.pds.model.employees.driver.driverDTO.DriverSimpleResponseDTO;
import com.example.pds.model.offices.Office;
import com.example.pds.model.packages.DeliveryType;
import com.example.pds.model.packages.statuses.Status;
import com.example.pds.model.transaction.Transaction;
import com.example.pds.model.user.userDTO.UserReceivePackageDTO;
import com.example.pds.model.user.userDTO.UserSimpleResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class PackageComplexResponseDTO {
    private int id;
    private UserReceivePackageDTO recipient;
    private Boolean isFragile;
    private String trackingNumber;
    private String description;
    private Boolean isSigned;
    private UserSimpleResponseDTO sender;
    private Office address;
    private DriverSimpleResponseDTO driver;
    private Status status;
    private Office office;
    private Transaction transaction;
    private DeliveryType deliveryType;
    private Double volume;
    private Double weight;
    private LocalDate dueDate;


}
