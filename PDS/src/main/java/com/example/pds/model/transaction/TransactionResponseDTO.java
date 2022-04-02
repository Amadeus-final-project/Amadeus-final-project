package com.example.pds.model.transaction;

import com.example.pds.model.user.User;
import com.example.pds.model.user.userDTO.UserComplexResponseDTO;
import com.example.pds.model.user.userDTO.UserSimpleResponseDTO;
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
