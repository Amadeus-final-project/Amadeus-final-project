package com.example.pds.controllers.profiles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilesRepository extends JpaRepository<Profile,Integer> {

        Profile findByUsername(String username);

        Profile findByEmail(String email);

        Profile findById(int id);

}
