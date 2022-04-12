package com.example.pds.model.vacations;

import com.example.pds.controllers.profiles.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vacations")
@NoArgsConstructor
@Getter
@Setter
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;


    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column
    private String description;

    @Column
    private boolean isApproved;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private VacationType vacationType;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @Column
    private boolean isRejected;

    public Vacation(LocalDate startDate, LocalDate endDate, String description, VacationType vacationType, Profile profile) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.isApproved = false;
        this.vacationType = vacationType;
        this.profile = profile;
        this.isRejected = false;
    }
}
