package com.example.pds.repository;

import com.example.pds.model.entity.Package;
import com.example.pds.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer> {
    List<Package> findAllByRecipient(User recipient);

    Package findByRecipient(int id);

    Package findById(int id);

    List<Package> findAllByStatusId(int id);
}
