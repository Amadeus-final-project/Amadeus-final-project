package com.example.pds.model.transaction;

import com.example.pds.controllers.profiles.Profile;
import com.example.pds.model.user.UserProfile;
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
    private String paymentType;
    private UserProfile payer;
    private BigDecimal price;
    private LocalDate paidAt;
}
