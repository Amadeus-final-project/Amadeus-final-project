package com.example.pds.model.employees.agent;

import com.example.pds.config.CheckViolations;
import com.example.pds.model.employees.agent.agentDTO.AgentEditProfileDTO;
import com.example.pds.model.packages.Package;
import com.example.pds.model.packages.PackageRepository;
import com.example.pds.model.packages.statuses.StatusRepository;
import com.example.pds.profiles.ProfilesRepository;
import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Validator;

@Service
public class AgentService {

    @Autowired
    Validator validator;
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ProfilesRepository profilesRepository;
    @Autowired
    PackageRepository packageRepository;
    @Autowired
    StatusRepository statusRepository;

@Transactional
    public void editProfile(int id, AgentEditProfileDTO agentDTO) {

        CheckViolations.check(validator, agentDTO);

        AgentProfile agent = agentRepository.findByProfileId(id);

        if (!agent.getFirstName().equals(agentDTO.getFirstName())) {
            agent.setFirstName(agentDTO.getFirstName());
        }
        if (!agent.getLastName().equals(agentDTO.getLastName())) {
            agent.setLastName(agentDTO.getLastName());
        }

        if (!agent.getPhoneNumber().equals(agentDTO.getPhoneNumber())) {
            agent.setPhoneNumber(agentDTO.getPhoneNumber());
        }
        agentRepository.save(agent);
    }
    public void approvePackage(int id) {
        Package pack = packageRepository.findById(id);
        if (pack == null) {
            throw new NotFoundException("Package Not Found");
        }
        if (pack.getStatus().getId()!= 1){
            throw new BadRequestException("This package isn't waiting for approval");
        }
        //status(2) -> approved
        pack.setStatus(statusRepository.findStatusById(2));
        packageRepository.save(pack);


    }

    public void disapprovePackage(int id) {
        Package pack = packageRepository.findById(id);
        if (pack == null) {
            throw new NotFoundException("Package Not Found");
        }
        if (pack.getStatus().getId()!= 1){
            throw new BadRequestException("This package isn't waiting for approval");
        }
        //status(0) -> disapproved
        pack.setStatus(statusRepository.findStatusById(0));
        packageRepository.save(pack);


    }
}


