package com.example.pds.model.packages.packageDTO;

import com.example.pds.model.user.userDTO.UserReceivePackageDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PackageSimpleResponseDTO {
    private int id;
    private UserReceivePackageDTO recipient;
    private Boolean isFragile;
    private String trackingNumber;
    private String description;
    private Boolean isSigned;
    private Double weight;
}
