package com.example.pds.model.vacations;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@NoArgsConstructor
public class VacationInformationDTO {

    private int vacationId;

    private int employeeId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    private String firstName;

    private String lastName;

    private String roleName;

}
