package com.example.pds.model.packages;

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

    //TODO make it less stupid
    Package findByRecipient(UserProfile recipient);

    Package findById(int id);

    List<Package> findAllByStatusId(int id, Pageable page);
}
