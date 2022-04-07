package com.example.pds.model.transaction;

import com.example.pds.model.user.UserProfile;
import com.example.pds.profiles.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAll();

    Transaction getTransactionById(int id);

    List<Transaction> findAllByPayer(UserProfile recipient);
}
