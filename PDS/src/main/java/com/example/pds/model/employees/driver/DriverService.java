package com.example.pds.model.employees.driver;

import com.example.pds.model.employees.EmployeeLoginDTO;
import com.example.pds.model.employees.EmployeeSimpleResponseDTO;
import com.example.pds.model.employees.employeeInfo.EmployeeInfo;
import com.example.pds.model.employees.employeeInfo.EmployeeRepository;
import com.example.pds.model.user.User;
import com.example.pds.model.user.UserRepository;
import com.example.pds.model.user.userDTO.RegisterDTO;
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
import java.util.Optional;
import java.util.Set;

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
        Vehicle oldVehicle = vehicleRepository.getById(driver.getVehicle().getId());
        if (oldVehicle!=null){
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
}
