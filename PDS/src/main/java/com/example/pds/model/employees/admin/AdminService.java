package com.example.pds.model.employees.admin;

import com.example.pds.config.CheckAuthentications;
import com.example.pds.config.CheckViolations;
import com.example.pds.model.employees.EmployeeLoginDTO;
import com.example.pds.model.employees.agent.Agent;
import com.example.pds.model.employees.agent.AgentRepository;
import com.example.pds.model.employees.agent.agentDTO.AgentRegisterDTO;
import com.example.pds.model.employees.driver.DriverProfile;
import com.example.pds.model.employees.driver.DriverRepository;
import com.example.pds.model.employees.driver.driverDTO.DriverRegisterDTO;
import com.example.pds.model.vehicle.Vehicle;
import com.example.pds.model.vehicle.VehicleComplexDTO;
import com.example.pds.model.vehicle.VehicleRepository;
import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

//    public EmployeeSimpleResponseDTO loginAdmin(EmployeeLoginDTO login) {
//
//        CheckViolations.check(validator, login);
//
//
//        Admin admin = adminRepository.findByEmail(login.getEmail());
//        if (admin == null) {
//            throw new NotFoundException("No such user");
//        }
//        //if (!passwordEncoder.matches(login.getPassword(), driver.getPassword())) {
//        //  throw new BadRequestException("Wrong credentials");
//        //}
//        EmployeeInfo employeeInfo = employeeRepository.findById(admin.getId());
//        return modelMapper.map(employeeInfo, EmployeeSimpleResponseDTO.class);
//    }

//    public EmployeeSimpleResponseDTO addDriver(Object isAdmin, DriverRegisterDTO driverRegisterDTO) {
//
//        CheckAuthentications.checkIfAdmin(isAdmin);
//
//        if (driverRepository.findByEmail(driverRegisterDTO.getEmail()) != null) {
//            throw new BadRequestException("Email already exists");
//        }
//
//        DriverProfile driver = new DriverProfile();
//        driver.setEmail(driverRegisterDTO.getEmail());
//        driver.setPassword(passwordEncoder.encode(driverRegisterDTO.getPassword()));
//
//        EmployeeInfo employeeInfo = modelMapper.map(driverRegisterDTO, EmployeeInfo.class);
//        driver.setEmployeeInfo(employeeInfo);
//
//        employeeRepository.save(employeeInfo);
//        driverRepository.save(driver);
//
//        return modelMapper.map(driver.getEmployeeInfo(), EmployeeSimpleResponseDTO.class);
//    }


//    public EmployeeSimpleResponseDTO addAgent(Object isAdmin, AgentRegisterDTO agentRegisterDTO) {
//
//        CheckAuthentications.checkIfAdmin(isAdmin);
//
//        if (agentRepository.findByEmail(agentRegisterDTO.getEmail()) != null) {
//            throw new BadRequestException("Email already exists");
//        }
//
//        Agent agent = new Agent();
//        agent.setEmail(agentRegisterDTO.getEmail());
//        agent.setPassword(passwordEncoder.encode(agentRegisterDTO.getPassword()));
//
//        EmployeeInfo employeeInfo = modelMapper.map(agentRegisterDTO, EmployeeInfo.class);
//        agent.setEmployeeInfo(employeeInfo);
//
//        employeeRepository.save(employeeInfo);
//        agentRepository.save(agent);
//
//        return modelMapper.map(agent.getEmployeeInfo(), EmployeeSimpleResponseDTO.class);
//    }

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
        Agent agent = agentRepository.getById(id);
        agentRepository.delete(agent);
    }
}
