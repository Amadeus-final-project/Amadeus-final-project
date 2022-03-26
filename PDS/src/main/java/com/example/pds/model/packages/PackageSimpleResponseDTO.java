package com.example.pds.model.packages;

import com.example.pds.model.address.Address;
import com.example.pds.model.employees.driver.Driver;
import com.example.pds.model.offices.Office;
import com.example.pds.model.transaction.Transaction;
import com.example.pds.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class PackageSimpleResponseDTO {
    private int id;
    private User recipient;
    private Boolean isFragile;
    private String trackingNumber;
    private String description;
}
