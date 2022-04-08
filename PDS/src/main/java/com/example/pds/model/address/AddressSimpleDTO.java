package com.example.pds.model.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
@Setter
@Getter
@NoArgsConstructor
//TODO validate city names
public class AddressSimpleDTO {
    private String country;
    private String city;
    private String postCode;
    private String region;
}
