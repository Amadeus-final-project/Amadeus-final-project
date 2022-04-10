package com.example.pds.model.packages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryTypeRepository extends JpaRepository<DeliveryType, Integer> {

    DeliveryType findByType(String type);
}
