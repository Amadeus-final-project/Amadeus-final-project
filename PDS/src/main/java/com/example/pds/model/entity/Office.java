package com.example.pds.model.entity;

import com.example.pds.model.entity.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "offices")
@Getter
@Setter
@NoArgsConstructor
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
