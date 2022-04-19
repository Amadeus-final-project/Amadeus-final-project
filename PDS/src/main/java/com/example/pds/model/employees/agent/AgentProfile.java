package com.example.pds.model.employees.agent;

import com.example.pds.controllers.profiles.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "agents_profile")
@Getter
@Setter
@NoArgsConstructor
public class AgentProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @Column(name = "available_paid_leave")
    private int availablePaidLeave;

    @Column(name = "agent_status")
    private String agentStatus;


}