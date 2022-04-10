package com.example.pds.model.packages.statuses;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "statuses")
@NoArgsConstructor
@AllArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    String currentStatus;
}
