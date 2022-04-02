package com.example.pds.service;

import com.example.pds.model.entity.Transaction;
import com.example.pds.repository.TransactionRepository;
import com.example.pds.model.dto.TransactionResponseDTO;
import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<TransactionResponseDTO> getTransactions() {

        List<TransactionResponseDTO> responseTransactions = new ArrayList<>();

        List<Transaction> transactions = transactionRepository.findAll();
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        for (Transaction transaction1 : transactions) {
            responseTransactions.add(modelMapper.map(transaction1, TransactionResponseDTO.class));
        }
        return responseTransactions;
    }

    public TransactionResponseDTO getTransactionById(int id) {


        if (transactionRepository.getTransactionById(id) == null) {
            throw new NotFoundException("Package does not exist");
        }
        Transaction transaction = transactionRepository.getTransactionById(id);
        return modelMapper.map(transaction, TransactionResponseDTO.class);
    }
}

