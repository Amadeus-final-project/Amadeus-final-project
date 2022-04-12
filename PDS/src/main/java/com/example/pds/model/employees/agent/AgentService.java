package com.example.pds.model.employees.agent;

import com.example.pds.controllers.profiles.Profile;
import com.example.pds.model.employees.agent.agentDTO.AgentEditProfileDTO;
import com.example.pds.model.employees.agent.agentDTO.AgentRequestVacationDTO;
import com.example.pds.model.packages.DeliveryType;
import com.example.pds.model.packages.Package;
import com.example.pds.model.packages.PackageRepository;
import com.example.pds.model.packages.statuses.StatusRepository;
import com.example.pds.controllers.profiles.ProfilesRepository;
import com.example.pds.model.vacations.*;
import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Validator;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    VacationRepository vacationRepository;

    @Autowired
    VacationTypeRepository vacationTypeRepository;

    @Transactional
    public void editProfile(int id, AgentEditProfileDTO agentDTO) {


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
        if (pack.getStatus().getId() != 1) {
            throw new BadRequestException("This package isn't waiting for approval");
        }
        //status(2) -> approved
        DeliveryType deliveryType = pack.getDeliveryType();

        LocalDate dueDate = LocalDate.now();

        switch (deliveryType.getType()) {
            case "STANDARD":
                dueDate = dueDate.plusDays(3);
                break;
            case "EXPRESS":
                dueDate = dueDate.plusDays(1);
                break;
            default:
                break;
        }

        pack.setDueDate(dueDate);
        pack.setStatus(statusRepository.findStatusById(2));
        packageRepository.save(pack);


    }

    public void disapprovePackage(int id) {
        Package pack = packageRepository.findById(id);
        if (pack == null) {
            throw new NotFoundException("Package Not Found");
        }
        if (pack.getStatus().getId() != 1) {
            throw new BadRequestException("This package isn't waiting for approval");
        }
        //status(0) -> disapproved
        pack.setStatus(statusRepository.findStatusById(0));
        packageRepository.save(pack);

    }

    public String requestVacation(int id, AgentRequestVacationDTO dto) {

        LocalDate startDate = dto.getStartDate();
        LocalDate endDate = dto.getEndDate();
        String description = dto.getDescription();
        String type = dto.getVacationType();

        AgentProfile agent = agentRepository.findByProfileId(id);

        Profile profile = agent.getProfile();

        VacationType vacationType = vacationTypeRepository.findByType(type);

        if (startDate.isBefore(LocalDate.now()) || endDate.isBefore(LocalDate.now())) {
            throw new BadRequestException("All dates must be in the future");
        }

        if (endDate.isBefore(startDate)) {
            throw new BadRequestException("End date must be after start date");
        }

        int lengthOfVacation = (int) ChronoUnit.DAYS.between(startDate, endDate);

        if (vacationType.getType().equals("PAID_LEAVE") && lengthOfVacation > agent.getAvailablePaidLeave()) {
            throw new BadRequestException("Not enough available paid leave days.");
        }

        Vacation vacation = new Vacation(startDate, endDate, description, vacationType, profile);

        this.vacationRepository.save(vacation);

        return "Vacation booked successfully.";
    }

    public List<VacationSimpleInfoDTO> getAllMyVacations(int id) {
        List<Vacation> vacations = vacationRepository.getAllByProfileId(id);

        List<VacationSimpleInfoDTO> DTOs = new ArrayList<>();

        for (Vacation vacation : vacations) {
            VacationSimpleInfoDTO dto = new VacationSimpleInfoDTO();

            dto.setStartDate(vacation.getStartDate());
            dto.setEndDate(vacation.getEndDate());
            dto.setApproved(vacation.isApproved());
            dto.setDescription(vacation.getDescription());
            DTOs.add(dto);
        }

        return DTOs;

    }


}


