package com.example.pds.controller;

import com.example.pds.model.employees.driver.driverDTO.DriverSimpleResponseDTO;
import com.example.pds.model.vehicle.VehicleService;
import com.example.pds.model.vehicle.vehicleProperties.VehicleComplexResponseDTO;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;


    @GetMapping("vehicle/getAllVehicles")
    @ResponseStatus(code = HttpStatus.OK)
    public List<VehicleComplexResponseDTO> getAllDrivers(HttpServletRequest request){
        Object isUser = request.getSession().getAttribute(Constants.IS_USER);
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        List<VehicleComplexResponseDTO> vehicles=vehicleService.getAllVehicles(isUser, isLogged);
        return vehicles;
    }

    @GetMapping("vehicle/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public VehicleComplexResponseDTO getVehicle(@PathVariable int id, HttpServletRequest request){
        Object isUser = request.getSession().getAttribute(Constants.IS_USER);
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        VehicleComplexResponseDTO vehicle = vehicleService.getVehicleById(id,isUser, isLogged);
        return vehicle;
    }
}
