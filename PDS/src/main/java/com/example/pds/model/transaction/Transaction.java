package com.example.pds.model.transaction;

import com.example.pds.controllers.profiles.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "payer_id", referencedColumnName = "id")
    private Profile payer;
    @Column
    private BigDecimal price;
    @Column(name = "paid_at")
    private LocalDate paidAt = LocalDate.now();
    @Column
    private String paymentType;
}
