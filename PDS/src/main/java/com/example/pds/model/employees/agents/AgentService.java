package com.example.pds.model.employees.agents;

import com.example.pds.model.employees.EmployeeLoginDTO;
import com.example.pds.model.employees.EmployeeSimpleResponseDTO;
import com.example.pds.model.employees.driver.Driver;
import com.example.pds.model.employees.employeeInfo.EmployeeInfo;
import com.example.pds.model.employees.employeeInfo.EmployeeProfileChangeDTO;
import com.example.pds.model.employees.employeeInfo.EmployeeRepository;
import com.example.pds.model.user.User;
import com.example.pds.model.user.userDTO.UserComplexResponseDTO;
import com.example.pds.model.user.userDTO.UserProfileChangeDTO;
import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class AgentService {

    @Autowired
    Validator validator;
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    EmployeeRepository employeeRepository;


    public EmployeeSimpleResponseDTO login(EmployeeLoginDTO login) {
        Set<ConstraintViolation<EmployeeLoginDTO>> violations = validator.validate(login);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<EmployeeLoginDTO> violation : violations) {
                throw new BadRequestException(violation.getMessage());
            }
        }

        Agent agent = agentRepository.findByEmail(login.getEmail());
        if (agent == null) {
            throw new NotFoundException("Agent not found");
        }

        return modelMapper.map(agent, EmployeeSimpleResponseDTO.class);
    }


    public EmployeeSimpleResponseDTO editProfile(Object id, EmployeeProfileChangeDTO employeeProfileChangeDTO, Object isAgent) {

        if (id == null) {
            throw new BadRequestException("You must login first");
        }
        if (isAgent == null) {
            throw new BadRequestException("You are not an agent");
        }

        Set<ConstraintViolation<EmployeeProfileChangeDTO>> violations = validator.validate(employeeProfileChangeDTO);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<EmployeeProfileChangeDTO> violation : violations) {
                throw new BadRequestException(violation.getMessage());
            }
        }
        Agent agent = agentRepository.getById((int) id);
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


