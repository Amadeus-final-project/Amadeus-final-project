package com.example.pds.model.packages;

import com.example.pds.model.address.Address;
import com.example.pds.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "packages")
@Setter
@Getter
@NoArgsConstructor
public class Package {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="sender_id", referencedColumnName = "id" )
    private User sender;
    @ManyToOne
    @JoinColumn(name="recipient_id", referencedColumnName = "id" )
    private User recipient;
    @ManyToOne
    @JoinColumn(name="delivery_address_id", referencedColumnName = "id")
    private Address address;







}
