package com.example.pds.model.vacations;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class VacationSimpleInfoDTO {

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern="yyyy/MM/dd")
    private LocalDate startDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern="yyyy/MM/dd")
    private LocalDate endDate;

    private String description;

    private boolean isApproved;

}
