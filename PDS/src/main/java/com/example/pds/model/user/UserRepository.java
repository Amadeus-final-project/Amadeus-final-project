package com.example.pds.model.user;

import com.example.pds.profiles.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserProfile,Integer> {


    UserProfile findByProfileId(int profileId);

}
