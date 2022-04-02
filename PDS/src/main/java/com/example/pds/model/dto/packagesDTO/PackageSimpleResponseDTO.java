package com.example.pds.model.dto.packagesDTO;

import com.example.pds.model.dto.userDTO.UserReceivePackageDTO;
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
