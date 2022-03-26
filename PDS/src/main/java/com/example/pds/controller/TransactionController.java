package com.example.pds.controller;

import com.example.pds.model.transaction.TransactionResponseDTO;
import com.example.pds.model.transaction.TransactionService;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("transaction/getAllTransactions")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TransactionResponseDTO> getAllTransactions(HttpServletRequest request) {
        Object isAdmin = request.getSession().getAttribute(Constants.IS_ADMIN);
        Object isAgent = request.getSession().getAttribute(Constants.IS_AGENT);
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        List<TransactionResponseDTO> transaction = transactionService.getTransactions(isAdmin, isAgent, isLogged);
        return transaction;
    }

    @GetMapping("transaction/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public TransactionResponseDTO getTransaction(@PathVariable int id, HttpServletRequest request) {
        Object isAdmin = request.getSession().getAttribute(Constants.IS_ADMIN);
        Object isAgent = request.getSession().getAttribute(Constants.IS_AGENT);
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        TransactionResponseDTO transaction = transactionService.getTransactionById(id, isAdmin, isAgent, isLogged);
        return transaction;
    }

}
