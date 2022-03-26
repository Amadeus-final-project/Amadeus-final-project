package com.example.pds.model.transaction;

import com.example.pds.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAll();

    Transaction getTransactionById(int id);

    List<Transaction> findAllByPayer(User recipient);
}
