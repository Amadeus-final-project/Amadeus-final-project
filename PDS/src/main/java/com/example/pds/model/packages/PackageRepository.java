package com.example.pds.model.packages;

import com.example.pds.model.employees.driver.DriverProfile;
import com.example.pds.model.offices.Office;
import com.example.pds.model.packages.statuses.Status;
import com.example.pds.model.user.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer> {
    Page<Package> findAllByRecipient(UserProfile recipient, Pageable page);

    Page<Package> findAll(Pageable page);

    Package findById(int id);

    List<Package> findAllByStatusId(int id, Pageable page);

    List<Package> findAllByDriverAndDeliveryOffice(DriverProfile driverProfile, Office office);

    List<Package> findAllByOfficeAndStatus(Office office, Status status);
}
