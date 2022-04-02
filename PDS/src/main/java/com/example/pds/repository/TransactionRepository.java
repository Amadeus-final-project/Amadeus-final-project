package com.example.pds.repository;

import com.example.pds.model.entity.Transaction;
import com.example.pds.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAll();

    Transaction getTransactionById(int id);

    List<Transaction> findAllByPayer(User recipient);
}
