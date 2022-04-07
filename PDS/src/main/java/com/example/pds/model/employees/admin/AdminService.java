package com.example.pds.model.employees.admin;

import com.example.pds.model.employees.agent.AgentProfile;
import com.example.pds.model.employees.agent.AgentRepository;
import com.example.pds.model.employees.agent.agentDTO.AgentRegisterDTO;
import com.example.pds.model.employees.driver.DriverProfile;
import com.example.pds.model.employees.driver.DriverRepository;
import com.example.pds.model.employees.driver.driverDTO.DriverRegisterDTO;
import com.example.pds.model.roles.RoleRepository;
import com.example.pds.model.vehicle.Vehicle;
import com.example.pds.model.vehicle.VehicleComplexDTO;
import com.example.pds.model.vehicle.VehicleRepository;
import com.example.pds.profiles.Profile;
import com.example.pds.profiles.ProfilesRepository;
import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Validator;

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
}
