package com.example.pds.controllers;

import com.example.pds.model.transaction.TransactionResponseDTO;
import com.example.pds.model.transaction.TransactionService;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/getAllTransactions")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TransactionResponseDTO> getAllTransactions(Pageable page) {
        List<TransactionResponseDTO> transaction = transactionService.getTransactions(page);
        return transaction;
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public TransactionResponseDTO getTransaction(@PathVariable int id) {
        TransactionResponseDTO transaction = transactionService.getTransactionById(id);
        return transaction;
    }

    @GetMapping("/getAllTransaction/{username}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TransactionResponseDTO> getTranscationByUser(@PathVariable String username){
        List<TransactionResponseDTO> transaction = transactionService.getTransactionByUsername(username);
        return transaction;
    }

}
