package com.example.pds.repository;

import com.example.pds.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {



    User findByUsername(String username);

    User findByEmail(String email);

    User findById(int id);

}
