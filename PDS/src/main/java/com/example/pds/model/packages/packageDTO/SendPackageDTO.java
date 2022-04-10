package com.example.pds.model.packages.packageDTO;

import com.example.pds.model.offices.Office;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SendPackageDTO {
    private int id;
    private String recipient;
    private String deliveryOffice;
    private String deliveryToOffice;
    private Integer deliveryType;
    private Boolean isSigned;
    private Boolean isFragile;
    private String description;
    private Double weight;
    private Double height;
    private Double width;
    private Double length;
}
