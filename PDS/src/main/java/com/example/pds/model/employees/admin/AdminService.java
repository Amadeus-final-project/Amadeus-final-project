package com.example.pds.model.employees.admin;

import com.example.pds.model.employees.agent.AgentProfile;
import com.example.pds.model.employees.agent.AgentRepository;
import com.example.pds.model.employees.agent.agentDTO.AgentRegisterDTO;
import com.example.pds.model.employees.driver.DriverProfile;
import com.example.pds.model.employees.driver.DriverRepository;
import com.example.pds.model.employees.driver.driverDTO.DriverRegisterDTO;
import com.example.pds.model.roles.Role;
import com.example.pds.model.roles.RoleRepository;
import com.example.pds.model.vacations.Vacation;
import com.example.pds.model.vacations.VacationInformationDTO;
import com.example.pds.model.vacations.VacationRepository;
import com.example.pds.model.vehicle.Vehicle;
import com.example.pds.model.vehicle.VehicleComplexDTO;
import com.example.pds.model.vehicle.VehicleRepository;
import com.example.pds.controllers.profiles.Profile;
import com.example.pds.controllers.profiles.ProfilesRepository;
import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    Validator validator;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    DriverRepository driverRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private ProfilesRepository profilesRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VacationRepository vacationRepository;


    public void removeVehicle(int id) {
        if (vehicleRepository.findById(id) == null) {
            throw new NotFoundException("No vehicle found");
        }
        Vehicle vehicle = vehicleRepository.getById(id);
        vehicleRepository.delete(vehicle);
    }

    public VehicleComplexDTO addVehicle(VehicleComplexDTO vehicleComplexDTO) {
        Vehicle vehicle = modelMapper.map(vehicleComplexDTO, Vehicle.class);
        vehicleRepository.save(vehicle);
        return modelMapper.map(vehicle, VehicleComplexDTO.class);
    }

    @Transactional
    public DriverProfile addDriver(DriverRegisterDTO driverRegisterDTO) {

        if (profilesRepository.findByEmail(driverRegisterDTO.getEmail()) != null) {
            throw new BadRequestException("Email already exists");
        }
        Profile profile = new Profile();
        profile.setUsername(driverRegisterDTO.getEmail());
        profile.setPassword(passwordEncoder.encode(driverRegisterDTO.getPassword()));
        profile.setEmail(driverRegisterDTO.getEmail());
        profile.setRole(roleRepository.findRoleById(2));
        profilesRepository.save(profile);

        DriverProfile driver = new DriverProfile();
        driver.setFirstName(driverRegisterDTO.getFirstName());
        driver.setLastName(driverRegisterDTO.getLastName());
        driver.setPhoneNumber(driverRegisterDTO.getPhoneNumber());
        driver.setProfile(profile);
        driverRepository.save(driver);

        return driver;
    }

    @Transactional
    public AgentProfile addAgent(AgentRegisterDTO agentRegisterDTO) {

        if (profilesRepository.findByEmail(agentRegisterDTO.getEmail()) != null) {
            throw new BadRequestException("Email already exists");
        }
        Profile profile = new Profile();
        profile.setUsername(agentRegisterDTO.getEmail());
        profile.setPassword(passwordEncoder.encode(agentRegisterDTO.getPassword()));
        profile.setEmail(agentRegisterDTO.getEmail());
        profile.setRole(roleRepository.findRoleById(3));
        profilesRepository.save(profile);

        AgentProfile agent = new AgentProfile();
        agent.setFirstName(agentRegisterDTO.getFirstName());
        agent.setLastName(agentRegisterDTO.getLastName());
        agent.setPhoneNumber(agentRegisterDTO.getPhoneNumber());
        agent.setProfile(profile);
        agentRepository.save(agent);

        return agent;
    }

    public void removeDriver(int id) {
        if (driverRepository.findById(id) == null) {
            throw new NotFoundException("No such driver");
        }
        DriverProfile driver = driverRepository.getById(id);
        driverRepository.delete(driver);
    }

    public void removeAgent(int id) {
        if (agentRepository.findById(id) == null) {
            throw new NotFoundException("No such agent");
        }
        AgentProfile agent = agentRepository.getById(id);
        agentRepository.delete(agent);
    }

    public List<VacationInformationDTO> getAllUnapprovedVacations() {
        List<Vacation> vacations = vacationRepository.getAllByApprovedFalse();

        List<VacationInformationDTO> DTOs = new ArrayList<>();

        for (Vacation vacation : vacations) {
            VacationInformationDTO dto = new VacationInformationDTO();


            dto.setId(vacation.getProfile().getId());
            dto.setStartDate(vacation.getStartDate());
            dto.setEndDate(vacation.getEndDate());
            dto.setDescription(vacation.getDescription());

            // TODO: Move firstName and lastName fields from AgentProfile, DriverProfile and UserProfile to Profile Class (DB table)
            // dto.setFirstName(vacation.getProfile().getFirstName());
            // dto.setLastName(vacation.getProfile().getLastName());

            dto.setRoleName(vacation.getProfile().getRole().getName());

            DTOs.add(dto);
        }
        return DTOs;
    }

    public String reviewVacation(VacationInformationDTO dto, boolean isApproved) {

        int id = dto.getId();

        Vacation vacation = vacationRepository.getById(id);

        if (isApproved) {
            vacation.setApproved(true);
            vacationRepository.save(vacation);
            return String.format("Vacation of employee %s %s from %s to %s approved successfully.",
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getStartDate().toString(),
                    dto.getEndDate().toString());
        } else {
            return String.format("Vacation of employee %s %s from %s to %s denied.",
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getStartDate().toString(),
                    dto.getEndDate().toString());
        }

        //TODO: Send email to employee?
    }
}
