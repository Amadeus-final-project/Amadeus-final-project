package com.example.pds.model.offices;

import com.example.pds.model.address.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
public class OfficeAddDTO {
    private String name;
    private String country;
    private String city;
    private String postcode;
    private String region;
    private String street;
}
