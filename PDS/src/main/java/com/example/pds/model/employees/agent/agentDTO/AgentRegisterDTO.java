package com.example.pds.model.employees.agent.agentDTO;


import com.example.pds.model.employees.employeeInfo.EmployeeInfo;
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
    private EmployeeInfo employeeInfo;
}
