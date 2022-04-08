package com.example.pds.model.vacations;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class VacationSimpleInfoDTO {

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    private boolean isApproved;
}
