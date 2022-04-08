package com.example.pds.model.packages;

import com.example.pds.model.address.Address;
import com.example.pds.model.offices.Office;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PackageDriverRelatedInformationDTO {
    private Office currentLocation;
    private Address deliveryAddress;
}
