package com.example.pds.model.dto.driverDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DriverRegisterDTO {

    private Integer id;
    private String email;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Boolean isWorking;
    private Integer remainingPaidLeave;


}