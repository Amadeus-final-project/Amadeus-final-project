package com.example.pds.model.packages.packageDTO;

import com.example.pds.model.address.Address;
import com.example.pds.model.employees.driver.DriverProfile;
import com.example.pds.model.offices.Office;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PackageDriverRelatedInformationDTO {
    private Office deliveryOffice;
    private Office office;
}
