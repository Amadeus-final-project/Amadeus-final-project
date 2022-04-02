package com.example.pds.web.controllers;

import com.example.pds.service.VehicleService;
import com.example.pds.model.dto.vehicleDTO.VehicleComplexResponseDTO;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;


    @GetMapping("/getAllVehicles")
    @ResponseStatus(code = HttpStatus.OK)
    public List<VehicleComplexResponseDTO> getAllVehicles() {
        List<VehicleComplexResponseDTO> vehicles = vehicleService.getAllVehicles();
        return vehicles;
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public VehicleComplexResponseDTO getVehicle(@PathVariable int id ) {
        VehicleComplexResponseDTO vehicle = vehicleService.getVehicleById(id);
        return vehicle;
    }
}
