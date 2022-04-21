package com.example.pds.model.employees.agent.agentDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
@NoArgsConstructor
public class AgentSimpleResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    }

