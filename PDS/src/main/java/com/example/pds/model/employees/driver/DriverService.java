package com.example.pds.model.employees.driver;

import com.example.pds.config.CheckAuthentications;
import com.example.pds.config.CheckViolations;
import com.example.pds.model.employees.EmployeeLoginDTO;
import com.example.pds.model.employees.agent.AgentProfile;
import com.example.pds.model.employees.agent.agentDTO.AgentEditProfileDTO;
import com.example.pds.model.employees.driver.driverDTO.DriverEditProfileDTO;
import com.example.pds.model.employees.driver.driverDTO.DriverSimpleResponseDTO;
import com.example.pds.model.vehicle.Vehicle;
import com.example.pds.model.vehicle.VehicleRepository;
import com.example.pds.profiles.ProfilesRepository;
import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.*;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Validator validator;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ProfilesRepository profilesRepository;

//    public EmployeeSimpleResponseDTO login(EmployeeLoginDTO login) {
//
//        CheckViolations.check(validator, login);
//
//        DriverProfile driver = driverRepository.findByEmail(login.getEmail());
//        if (driver == null) {
//            throw new NotFoundException("Driver not found");
//        }
//        //if (!passwordEncoder.matches(login.getPassword(), driver.getPassword())) {
//        //  throw new BadRequestException("Wrong credentials");
//        //}
//        EmployeeInfo employeeInfo = employeeRepository.findById(driver.getId());
//        return modelMapper.map(employeeInfo, EmployeeSimpleResponseDTO.class);
//
//    }

    public void getVehicle(int id, int vehicleId) {
        DriverProfile driver = driverRepository.getById(id);

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

    public void releaseVehicle(int id) {
        DriverProfile driver = driverRepository.getById((id));
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
        List<DriverProfile> drivers = driverRepository.findAll();
        for (DriverProfile driver : drivers) {
            simpleDriver.add(modelMapper.map(driver, DriverSimpleResponseDTO.class));
        }
        return simpleDriver;
    }

    public DriverSimpleResponseDTO getDriverById(int driverId) {
        if (driverRepository.findById(driverId) == null) {
            throw new NotFoundException("Driver does not exist");
        }
        DriverProfile driver = driverRepository.getById(driverId);
        return modelMapper.map(driver, DriverSimpleResponseDTO.class);
    }

    //    public EmployeeSimpleResponseDTO editProfile(Object id, EmployeeProfileChangeDTO employeeProfileChangeDTO, Object isDriver) {
//
    public void editProfile(int id, DriverEditProfileDTO driverDTO) {

        CheckViolations.check(validator, driverDTO);

        DriverProfile driver = driverRepository.findByProfileId(id);

        if (!driver.getFirstName().equals(driverDTO.getFirstName())) {
            driver.setFirstName(driverDTO.getFirstName());
        }
        if (!driver.getLastName().equals(driverDTO.getLastName())) {
            driver.setLastName(driverDTO.getLastName());
        }

        if (!driver.getPhoneNumber().equals(driverDTO.getPhoneNumber())) {
            driver.setPhoneNumber(driverDTO.getPhoneNumber());
        }
        driverRepository.save(driver);
    }
}

// public void requestPaidLeave(Date start, Date end, String description, Object isLogged, Object isUser) {
//TODO
// }

