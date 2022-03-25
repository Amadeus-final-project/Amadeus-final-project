package com.example.pds.model.employees.admin;

import com.example.pds.model.employees.EmployeeLoginDTO;
import com.example.pds.model.employees.EmployeeSimpleResponseDTO;
import com.example.pds.model.employees.driver.Driver;
import com.example.pds.model.employees.employeeInfo.EmployeeInfo;
import com.example.pds.model.employees.employeeInfo.EmployeeRepository;
import com.example.pds.model.vehicle.Vehicle;
import com.example.pds.model.vehicle.VehicleComplexDTO;
import com.example.pds.model.vehicle.VehicleRepository;
import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import com.example.pds.util.exceptions.UnauthorizedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

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
    EmployeeRepository employeeRepository;
    public void removeVehicle(int id, Object isAdmin) {
        if (isAdmin==null){
            throw new UnauthorizedException("You are unauthorized");
        }
        if (vehicleRepository.findById(id)==null){
            throw new NotFoundException("No vehicle found");
        }
        Vehicle vehicle = vehicleRepository.getById(id);
        vehicleRepository.delete(vehicle);
    }

    public VehicleComplexDTO addVehicle(Object isAdmin, VehicleComplexDTO vehicleComplexDTO) {
        if (isAdmin==null){
            throw new UnauthorizedException("You are unauthorized");
        }
        Vehicle vehicle = modelMapper.map(vehicleComplexDTO,Vehicle.class);
        vehicleRepository.save(vehicle);
        return modelMapper.map(vehicle,VehicleComplexDTO.class);
    }

    public EmployeeSimpleResponseDTO loginAdmin(EmployeeLoginDTO login) {
        Set<ConstraintViolation<EmployeeLoginDTO>> violations = validator.validate(login);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<EmployeeLoginDTO> violation : violations) {
                throw new BadRequestException(violation.getMessage());
            }
        }
        System.out.println("stignah");

        Admin admin = adminRepository.findByEmail(login.getEmail());
        if (admin == null) {
            throw new NotFoundException("Driver not found");
        }
        //if (!passwordEncoder.matches(login.getPassword(), driver.getPassword())) {
        //  throw new BadRequestException("Wrong credentials");
        //}
        EmployeeInfo employeeInfo = employeeRepository.findById(admin.getId());
        return modelMapper.map(employeeInfo,EmployeeSimpleResponseDTO.class);
    }
}
