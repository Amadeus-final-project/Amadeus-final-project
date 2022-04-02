package com.example.pds.service;

import com.example.pds.model.dto.employeeInfoDTO.EmployeeLoginDTO;
import com.example.pds.model.dto.employeeInfoDTO.EmployeeSimpleResponseDTO;
import com.example.pds.repository.DriverRepository;
import com.example.pds.model.dto.driverDTO.DriverSimpleResponseDTO;
import com.example.pds.model.entity.EmployeeInfo;
import com.example.pds.model.dto.employeeInfoDTO.EmployeeProfileChangeDTO;
import com.example.pds.repository.EmployeeRepository;
import com.example.pds.model.entity.Driver;
import com.example.pds.model.entity.Vehicle;
import com.example.pds.repository.VehicleRepository;
import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeSimpleResponseDTO login(EmployeeLoginDTO login) {


        Driver driver = driverRepository.findByEmail(login.getEmail());
        if (driver == null) {
            throw new NotFoundException("Driver not found");
        }
        //if (!passwordEncoder.matches(login.getPassword(), driver.getPassword())) {
        //  throw new BadRequestException("Wrong credentials");
        //}
        EmployeeInfo employeeInfo = employeeRepository.findById(driver.getId());
        return modelMapper.map(employeeInfo, EmployeeSimpleResponseDTO.class);

    }

    public void getVehicle(int vehicleId) {

        Driver driver = driverRepository.getById((null));


        Vehicle vehicle = vehicleRepository.getById(vehicleId);
        if (driver.getVehicle() != null) {
            Vehicle oldVehicle = vehicleRepository.getById(driver.getVehicle().getId());
            oldVehicle.setIsAvailable(true);
            vehicleRepository.save(oldVehicle);
        }
        if (!vehicle.getIsAvailable()) {
            throw new BadRequestException("Vehicle is not available");
        }
        driver.setVehicle(vehicle);
        driverRepository.save(driver);
        vehicle.setIsAvailable(false);
        vehicleRepository.save(vehicle);
    }

    public void releaseVehicle() {

        Driver driver = driverRepository.getById(null);


        if (driver.getVehicle() != null) {
            Vehicle vehicle = driver.getVehicle();
            vehicle.setIsAvailable(true);
            vehicleRepository.save(vehicle);
            driver.setVehicle(null);
            driverRepository.save(driver);
        }

    }

    public List<DriverSimpleResponseDTO> getAllDrivers() {


        List<DriverSimpleResponseDTO> simpleDriver = new ArrayList<>();
        List<Driver> drivers = driverRepository.findAll();
        for (Driver driver : drivers) {
            simpleDriver.add(modelMapper.map(driver, DriverSimpleResponseDTO.class));


        }
        return simpleDriver;
    }

    public DriverSimpleResponseDTO getDriverById(int driverId) {


        if (driverRepository.findById(driverId) == null) {
            throw new NotFoundException("Driver does not exist");
        }
        Driver driver = driverRepository.getById(driverId);
        return modelMapper.map(driver, DriverSimpleResponseDTO.class);
    }

    public EmployeeSimpleResponseDTO editProfile(Object id, EmployeeProfileChangeDTO employeeProfileChangeDTO) {


        Driver driver = driverRepository.getById((int) id);
        if (!driver.getEmployeeInfo().getFirstName().equals(employeeProfileChangeDTO.getFirstName())) {
            driver.getEmployeeInfo().setFirstName(employeeProfileChangeDTO.getFirstName());
        }
        if (!driver.getEmployeeInfo().getLastName().equals(employeeProfileChangeDTO.getLastName())) {
            driver.getEmployeeInfo().setLastName(employeeProfileChangeDTO.getLastName());
        }

        if (driver.getEmployeeInfo().getPhoneNumber() == null) {
            driver.getEmployeeInfo().setPhoneNumber(employeeProfileChangeDTO.getPhoneNumber());
        }
        if (!driver.getEmployeeInfo().getPhoneNumber().equals(employeeProfileChangeDTO.getPhoneNumber())) {
            driver.getEmployeeInfo().setPhoneNumber(employeeProfileChangeDTO.getPhoneNumber());
        }
        driver.getEmployeeInfo().setPhoneNumber(employeeProfileChangeDTO.getPhoneNumber());

        driverRepository.save(driver);
        return modelMapper.map(driver.getEmployeeInfo(), EmployeeSimpleResponseDTO.class);
    }
}

// public void requestPaidLeave(Date start, Date end, String description, Object isLogged, Object isUser) {
//TODO
// }

