package com.example.pds.model.vehicle;

import com.example.pds.model.employees.driver.Driver;
import com.example.pds.model.employees.driver.driverDTO.DriverSimpleResponseDTO;
import com.example.pds.model.vehicle.vehicleProperties.VehicleComplexResponseDTO;
import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import com.example.pds.util.exceptions.UnauthorizedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    ModelMapper modelMapper;
    public List<VehicleComplexResponseDTO> getAllVehicles(Object isUser, Object isLogged) {
        if (isLogged==null){
            throw new BadRequestException("You are not logged in");

        }
        if (isUser!=null){
            System.out.println(isUser);
            throw new UnauthorizedException("You are unauthorized");
        }
        List<VehicleComplexResponseDTO> complexVehicle = new ArrayList<>();
        List<Vehicle> vehicles=vehicleRepository.findAll();
        for (Vehicle vehicle : vehicles) {
            complexVehicle.add(modelMapper.map(vehicle,VehicleComplexResponseDTO.class));
        }
        return complexVehicle;
    }

    public VehicleComplexResponseDTO getVehicleById(int id, Object isUser, Object isLogged) {
        if (isLogged==null){
            throw new BadRequestException("You are not logged in");

        }
        if (isUser!=null){
            System.out.println(isUser);
            throw new UnauthorizedException("You are unauthorized");
        }
        if (vehicleRepository.findById(id)==null){
            throw new NotFoundException("Driver does not exist");
        }
        Vehicle vehicle = vehicleRepository.getById(id);
        return modelMapper.map(vehicle,VehicleComplexResponseDTO.class);
    }
}
