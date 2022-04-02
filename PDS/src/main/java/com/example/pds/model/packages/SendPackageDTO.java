package com.example.pds.model.packages;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SendPackageDTO {
    private int id;
    private String recipient;

    private String deliveryType;
    private Boolean isSigned;
    private Boolean isFragile;
    private String description;
    private Double weight;
    private Double height;
    private Double width;
    private Double length;
}
