package com.example.pds.model.packages;

import com.example.pds.model.user.UserProfile;
import com.example.pds.profiles.Profile;
import com.sun.xml.bind.v2.runtime.reflect.Lister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer> {
    List<Package> findAllByRecipient(UserProfile recipient);

    List<Package> findAll();

    Package findByRecipient(UserProfile recipient);

    Package findById(int id);

    List<Package> findAllByStatusId(int id);
}
