package com.example.pds.service;

import com.example.pds.model.dto.employeeInfoDTO.EmployeeLoginDTO;
import com.example.pds.model.dto.employeeInfoDTO.EmployeeSimpleResponseDTO;
import com.example.pds.repository.AgentRepository;
import com.example.pds.model.dto.employeeInfoDTO.EmployeeProfileChangeDTO;
import com.example.pds.repository.EmployeeRepository;
import com.example.pds.model.entity.Agent;
import com.example.pds.repository.PackageRepository;
import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentService {


    @Autowired
    AgentRepository agentRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PackageRepository packageRepository;


    public EmployeeSimpleResponseDTO login(EmployeeLoginDTO login) {


        Agent agent = agentRepository.findByEmail(login.getEmail());

        if (agent == null) {
            throw new NotFoundException("Agent not found");
        }

        return modelMapper.map(agent, EmployeeSimpleResponseDTO.class);
    }


    public EmployeeSimpleResponseDTO editProfile(EmployeeProfileChangeDTO employeeProfileChangeDTO) {


        Agent agent = agentRepository.getById(employeeProfileChangeDTO.getId());

        if (!agent.getEmployeeInfo().getFirstName().equals(employeeProfileChangeDTO.getFirstName())) {
            agent.getEmployeeInfo().setFirstName(employeeProfileChangeDTO.getFirstName());
        }
        if (!agent.getEmployeeInfo().getLastName().equals(employeeProfileChangeDTO.getLastName())) {
            agent.getEmployeeInfo().setLastName(employeeProfileChangeDTO.getLastName());
        }

        if (agent.getEmployeeInfo().getPhoneNumber() == null) {
            agent.getEmployeeInfo().setPhoneNumber(employeeProfileChangeDTO.getPhoneNumber());
        }
        if (!agent.getEmployeeInfo().getPhoneNumber().equals(employeeProfileChangeDTO.getPhoneNumber())) {
            agent.getEmployeeInfo().setPhoneNumber(employeeProfileChangeDTO.getPhoneNumber());
        }
        agent.getEmployeeInfo().setPhoneNumber(employeeProfileChangeDTO.getPhoneNumber());

        agentRepository.save(agent);
        return modelMapper.map(agent.getEmployeeInfo(), EmployeeSimpleResponseDTO.class);
    }

}


