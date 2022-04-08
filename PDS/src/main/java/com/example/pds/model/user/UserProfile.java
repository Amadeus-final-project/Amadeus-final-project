package com.example.pds.model.user;

import com.example.pds.model.address.Address;
import com.example.pds.controllers.profiles.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users_profile")
@Setter
@Getter
@NoArgsConstructor

public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String phoneNumber;
    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;
    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

}
