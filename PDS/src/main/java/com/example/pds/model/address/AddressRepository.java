package com.example.pds.model.address;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    Address findAddressByCity(String cityName);
}
