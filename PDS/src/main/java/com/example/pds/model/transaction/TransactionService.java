package com.example.pds.model.transaction;

import com.example.pds.config.CheckAuthentications;
import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<TransactionResponseDTO> getTransactions(Object isAdmin, Object isAgent, Object isLogged, Pageable page) {

        CheckAuthentications.checkIfLogged(isLogged);
        CheckAuthentications.checkIfAdmin(isAdmin);


        List<TransactionResponseDTO> responseTransactions = new ArrayList<>();

        List<Transaction> transactions = transactionRepository.findAll(page).getContent();
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        for (Transaction transaction1 : transactions) {
            responseTransactions.add(modelMapper.map(transaction1, TransactionResponseDTO.class));
        }
        return responseTransactions;
    }

    public TransactionResponseDTO getTransactionById(int id, Object isAdmin, Object isAgent, Object isLogged) {

        CheckAuthentications.checkIfLogged(isLogged);
        CheckAuthentications.checkIfAdmin(isAdmin);


        if (transactionRepository.getTransactionById(id) == null) {
            throw new NotFoundException("Package does not exist");
        }
        Transaction transaction = transactionRepository.getTransactionById(id);
        return modelMapper.map(transaction, TransactionResponseDTO.class);
    }

    public TransactionResponseDTO createTransaction (Object isAdmin, Object isLogged,TransactionResponseDTO transactionResponseDTO){

        CheckAuthentications.checkIfLogged(isLogged);
        CheckAuthentications.checkIfAdmin(isAdmin);

        Transaction transaction = modelMapper.map(transactionResponseDTO, Transaction.class);
        transactionRepository.saveAndFlush(transaction);
        return modelMapper.map(transaction, TransactionResponseDTO.class);

    }

    public TransactionResponseDTO deleteTransaction (Object isAdmin, Object isLogged, int id) {

        CheckAuthentications.checkIfLogged(isLogged);
        CheckAuthentications.checkIfAdmin(isAdmin);

        Transaction transaction = transactionRepository.getTransactionById(id);

        if (transaction == null) {
            throw new NotFoundException("No such transaction");
        }

        transactionRepository.delete(transaction);
        return modelMapper.map(transaction, TransactionResponseDTO.class);

    }
}
