package com.example.pds.model.employees.driver;

import com.example.pds.model.employees.EmployeeLoginDTO;
import com.example.pds.model.employees.EmployeeSimpleResponseDTO;
import com.example.pds.model.employees.driver.driverDTO.DriverSimpleResponseDTO;
import com.example.pds.model.employees.employeeInfo.EmployeeInfo;
import com.example.pds.model.employees.employeeInfo.EmployeeProfileChangeDTO;
import com.example.pds.model.employees.employeeInfo.EmployeeRepository;
import com.example.pds.model.vehicle.Vehicle;
import com.example.pds.model.vehicle.VehicleRepository;
import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import com.example.pds.util.exceptions.UnauthorizedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
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
    private EmployeeRepository employeeRepository;

    public EmployeeSimpleResponseDTO login(EmployeeLoginDTO login) {

        Set<ConstraintViolation<EmployeeLoginDTO>> violations = validator.validate(login);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<EmployeeLoginDTO> violation : violations) {
                throw new BadRequestException(violation.getMessage());
            }
        }

        Driver driver = driverRepository.findByEmail(login.getEmail());
        if (driver == null) {
            throw new NotFoundException("Driver not found");
        }
        //if (!passwordEncoder.matches(login.getPassword(), driver.getPassword())) {
          //  throw new BadRequestException("Wrong credentials");
        //}
        EmployeeInfo employeeInfo = employeeRepository.findById(driver.getId());
        return modelMapper.map(employeeInfo,EmployeeSimpleResponseDTO.class);

    }

    public void getVehicle(Object id, int vehicleId, Object isDriver){
        if (id == null){
            throw new BadRequestException("You must log in first");
        }
        Driver driver = driverRepository.getById((int) id);
        if (isDriver==null){
            throw new BadRequestException("You are not a driver");
        }
        Vehicle vehicle = vehicleRepository.getById(vehicleId);
        if (driver.getVehicle()!=null) {
            Vehicle oldVehicle = vehicleRepository.getById(driver.getVehicle().getId());
            oldVehicle.setIsAvailable(true);
            vehicleRepository.save(oldVehicle);
        }
        if (!vehicle.getIsAvailable()){
            throw new BadRequestException("Vehicle is not available");
        }
        driver.setVehicle(vehicle);
        driverRepository.save(driver);
        vehicle.setIsAvailable(false);
        vehicleRepository.save(vehicle);
    }

    public void releaseVehicle(Object id, Object isDriver){
        if (id == null){
            throw new BadRequestException("You must log in first");
        }
        Driver driver = driverRepository.getById((int) id);
        if (isDriver==null){
            throw new BadRequestException("You are not a driver");
        }
        if (driver.getVehicle()!=null){
            Vehicle vehicle = driver.getVehicle();
            vehicle.setIsAvailable(true);
            vehicleRepository.save(vehicle);
            driver.setVehicle(null);
            driverRepository.save(driver);
        }

    }
    public List<DriverSimpleResponseDTO>getAllDrivers(Object isUser, Object isLogged){
        if (isLogged==null){
            throw new BadRequestException("You are not logged in");

        }
        if (isUser!=null){
            System.out.println(isUser);
            throw new UnauthorizedException("You are unauthorized");
        }
        List<DriverSimpleResponseDTO> simpleDriver = new ArrayList<>();
        List<Driver> drivers=driverRepository.findAll();
        for (Driver driver : drivers) {
            simpleDriver.add(modelMapper.map(driver,DriverSimpleResponseDTO.class));


        }
       return simpleDriver;
    }

    public DriverSimpleResponseDTO getDriverById(int driverId, Object isUser, Object isLogged) {
        if (isLogged==null){
            throw new BadRequestException("You are not logged in");

        }
        if (isUser!=null){
            System.out.println(isUser);
            throw new UnauthorizedException("You are unauthorized");
        }
        if (driverRepository.findById(driverId)==null){
            throw new NotFoundException("Driver does not exist");
        }
        Driver driver = driverRepository.getById(driverId);
        return modelMapper.map(driver,DriverSimpleResponseDTO.class);
    }

    public EmployeeSimpleResponseDTO editProfile(Object id, EmployeeProfileChangeDTO employeeProfileChangeDTO, Object isDriver) {
        if (id == null) {
            throw new BadRequestException("You must login first");
        }
        if (isDriver == null) {
            throw new BadRequestException("You are not a driver");
        }

        Set<ConstraintViolation<EmployeeProfileChangeDTO>> violations = validator.validate(employeeProfileChangeDTO);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<EmployeeProfileChangeDTO> violation : violations) {
                throw new BadRequestException(violation.getMessage());
            }
        }
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
