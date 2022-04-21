package com.example.pds.model.offices;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OfficeComplexResponseDTO {
    private Integer id;
    private String name;
    private String country;
    private String city;
    private String postcode;
    private String region;
    private String street;
}
