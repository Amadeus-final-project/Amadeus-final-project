package com.example.pds.model.employees.agent.agentDTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class AgentRegisterDTO {

    private int id;
    private String email;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Boolean isWorking;
    private Integer remainingPaidLeave;
}
