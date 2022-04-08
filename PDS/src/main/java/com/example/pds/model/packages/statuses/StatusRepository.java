package com.example.pds.model.packages.statuses;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status,Integer> {
    Status findStatusById(int id);
}
