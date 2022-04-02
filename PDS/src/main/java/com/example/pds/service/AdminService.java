package com.example.pds.service;

import com.example.pds.model.dto.employeeInfoDTO.EmployeeLoginDTO;
import com.example.pds.model.dto.employeeInfoDTO.EmployeeSimpleResponseDTO;
import com.example.pds.repository.AdminRepository;
import com.example.pds.model.entity.Agent;
import com.example.pds.repository.AgentRepository;
import com.example.pds.model.dto.AgentRegisterDTO;
import com.example.pds.model.entity.Driver;
import com.example.pds.repository.DriverRepository;
import com.example.pds.model.dto.driverDTO.DriverRegisterDTO;
import com.example.pds.model.entity.EmployeeInfo;
import com.example.pds.repository.EmployeeRepository;
import com.example.pds.model.entity.Admin;
import com.example.pds.model.entity.Vehicle;
import com.example.pds.model.dto.vehicleDTO.VehicleComplexDTO;
import com.example.pds.repository.VehicleRepository;
import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    EmployeeRepository employeeRepository;
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

    public EmployeeSimpleResponseDTO loginAdmin(EmployeeLoginDTO login) {



        Admin admin = adminRepository.findByEmail(login.getEmail());
        if (admin == null) {
            throw new NotFoundException("No such user");
        }
        //if (!passwordEncoder.matches(login.getPassword(), driver.getPassword())) {
        //  throw new BadRequestException("Wrong credentials");
        //}
        EmployeeInfo employeeInfo = employeeRepository.findById(admin.getId());
        return modelMapper.map(employeeInfo, EmployeeSimpleResponseDTO.class);
    }

    public EmployeeSimpleResponseDTO addDriver( DriverRegisterDTO driverRegisterDTO) {


        if (driverRepository.findByEmail(driverRegisterDTO.getEmail()) != null) {
            throw new BadRequestException("Email already exists");
        }

        Driver driver = new Driver();
        driver.setEmail(driverRegisterDTO.getEmail());
        driver.setPassword(passwordEncoder.encode(driverRegisterDTO.getPassword()));

        EmployeeInfo employeeInfo = modelMapper.map(driverRegisterDTO, EmployeeInfo.class);
        driver.setEmployeeInfo(employeeInfo);

        employeeRepository.save(employeeInfo);
        driverRepository.save(driver);

        return modelMapper.map(driver.getEmployeeInfo(), EmployeeSimpleResponseDTO.class);
    }


    public EmployeeSimpleResponseDTO addAgent(AgentRegisterDTO agentRegisterDTO) {


        if (agentRepository.findByEmail(agentRegisterDTO.getEmail()) != null) {
            throw new BadRequestException("Email already exists");
        }

        Agent agent = new Agent();
        agent.setEmail(agentRegisterDTO.getEmail());
        agent.setPassword(passwordEncoder.encode(agentRegisterDTO.getPassword()));

        EmployeeInfo employeeInfo = modelMapper.map(agentRegisterDTO, EmployeeInfo.class);
        agent.setEmployeeInfo(employeeInfo);

        employeeRepository.save(employeeInfo);
        agentRepository.save(agent);

        return modelMapper.map(agent.getEmployeeInfo(), EmployeeSimpleResponseDTO.class);
    }

    public void removeDriver(int id) {


        if (driverRepository.findById(id) == null) {
            throw new NotFoundException("No such driver");
        }
        Driver driver = driverRepository.getById(id);
        System.out.println(1);
        driverRepository.delete(driver);
        System.out.println(2);
    }

    public void removeAgent(int id) {


        if (agentRepository.findById(id) == null) {
            throw new NotFoundException("No such agent");
        }
        Agent agent = agentRepository.getById(id);
        agentRepository.delete(agent);
    }
}
