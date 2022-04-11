package com.example.pds.model.transaction;

import com.example.pds.model.user.userDTO.UserComplexResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class TransactionResponseDTO {

    private int id;
    private String paymentType;
    private UserComplexResponseDTO payer;
    private BigDecimal price;
    private LocalDate paidAt;
}
