package com.example.pds.model.transaction;

import com.example.pds.controllers.profiles.Profile;
import com.example.pds.model.user.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAll();

    Transaction getTransactionById(int id);

    List<Transaction> findAllByPayer(Profile recipient, Pageable page);
    List<Transaction> findAllByPayerUsername(String username);
}
