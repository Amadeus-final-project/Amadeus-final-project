package com.example.pds.model.dto.packagesDTO;

import com.example.pds.model.entity.Address;
import com.example.pds.model.dto.driverDTO.DriverSimpleResponseDTO;
import com.example.pds.model.entity.DeliveryType;
import com.example.pds.model.entity.Office;
import com.example.pds.model.entity.Status;
import com.example.pds.model.entity.Transaction;
import com.example.pds.model.dto.userDTO.UserReceivePackageDTO;
import com.example.pds.model.dto.userDTO.UserSimpleResponseDTO;
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
    private Address address;
    private DriverSimpleResponseDTO driver;
    private Status status;
    private Office office;
    private Transaction transaction;
    private DeliveryType deliveryType;
    private Double volume;
    private Double weight;
    private LocalDate dueDate;


}
