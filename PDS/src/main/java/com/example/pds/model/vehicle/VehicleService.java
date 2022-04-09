package com.example.pds.model.vehicle;

import com.example.pds.config.CheckAuthentications;
import com.example.pds.model.vehicle.vehicleProperties.VehicleComplexResponseDTO;
import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<VehicleComplexResponseDTO> getAllVehicles(Object isUser, Object isLogged, Pageable page) {

        CheckAuthentications.checkIfLogged(isLogged);
        CheckAuthentications.checkIfEmployee(isUser);

        List<VehicleComplexResponseDTO> complexVehicle = new ArrayList<>();
        List<Vehicle> vehicles = vehicleRepository.findAll(page).getContent();
        for (Vehicle vehicle : vehicles) {
            complexVehicle.add(modelMapper.map(vehicle, VehicleComplexResponseDTO.class));
        }
        return complexVehicle;
    }

    public VehicleComplexResponseDTO getVehicleById(int id, Object isUser, Object isLogged) {
        CheckAuthentications.checkIfLogged(isLogged);
        CheckAuthentications.checkIfUser(isUser);

        if (vehicleRepository.findById(id) == null) {
            throw new NotFoundException("Driver does not exist");
        }
        Vehicle vehicle = vehicleRepository.getById(id);
        return modelMapper.map(vehicle, VehicleComplexResponseDTO.class);
    }
}
