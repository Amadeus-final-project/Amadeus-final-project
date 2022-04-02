package com.example.pds.web.controllers;

import com.example.pds.model.vehicle.VehicleService;
import com.example.pds.model.vehicle.vehicleProperties.VehicleComplexResponseDTO;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<VehicleComplexResponseDTO> getAllVehicles(HttpServletRequest request) {
        Object isUser = request.getSession().getAttribute(Constants.IS_USER);
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        List<VehicleComplexResponseDTO> vehicles = vehicleService.getAllVehicles(isUser, isLogged);
        return vehicles;
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public VehicleComplexResponseDTO getVehicle(@PathVariable int id, HttpServletRequest request) {
        Object isUser = request.getSession().getAttribute(Constants.IS_USER);
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        VehicleComplexResponseDTO vehicle = vehicleService.getVehicleById(id, isUser, isLogged);
        return vehicle;
    }
}
