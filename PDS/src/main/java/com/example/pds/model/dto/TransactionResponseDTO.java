package com.example.pds.model.dto;

import com.example.pds.model.dto.userDTO.UserComplexResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class TransactionResponseDTO {

    private int id;
    private Boolean isPaid;
    private UserComplexResponseDTO payer;
    private BigDecimal price;
    private LocalDate paidAt;
}
