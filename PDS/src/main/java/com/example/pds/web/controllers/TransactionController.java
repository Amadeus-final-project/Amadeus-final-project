package com.example.pds.web.controllers;

import com.example.pds.model.dto.TransactionResponseDTO;
import com.example.pds.service.TransactionService;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/getAllTransactions")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TransactionResponseDTO> getAllTransactions() {
        List<TransactionResponseDTO> transaction = transactionService.getTransactions();
        return transaction;
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public TransactionResponseDTO getTransaction(@PathVariable int id) {
        TransactionResponseDTO transaction = transactionService.getTransactionById(id);
        return transaction;
    }

}
