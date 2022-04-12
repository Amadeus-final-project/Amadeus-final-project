package com.example.pds.controllers;

import com.example.pds.model.vehicle.VehicleService;
import com.example.pds.model.vehicle.vehicleProperties.VehicleComplexResponseDTO;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;


    @GetMapping("/getAllVehicles")
    @ResponseStatus(code = HttpStatus.OK)
    public List<VehicleComplexResponseDTO> getAllVehicles(Pageable page) {
        List<VehicleComplexResponseDTO> vehicles = vehicleService.getAllVehicles(page);
        return vehicles;
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public VehicleComplexResponseDTO getVehicle(@PathVariable int id) {
        VehicleComplexResponseDTO vehicle = vehicleService.getVehicleById(id);
        return vehicle;
    }
}
