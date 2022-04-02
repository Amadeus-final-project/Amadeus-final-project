package com.example.pds.web.controllers;

import com.example.pds.model.dto.employeeInfoDTO.EmployeeLoginDTO;
import com.example.pds.model.dto.employeeInfoDTO.EmployeeSimpleResponseDTO;
import com.example.pds.service.DriverService;
import com.example.pds.model.dto.driverDTO.DriverSimpleResponseDTO;
import com.example.pds.model.dto.employeeInfoDTO.EmployeeProfileChangeDTO;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<EmployeeSimpleResponseDTO> logIn(@RequestBody EmployeeLoginDTO driver) {
        EmployeeSimpleResponseDTO dto = driverService.login(driver);
        return ResponseEntity.status(200).body(dto);
    }

    @PostMapping("/logout")
    @ResponseStatus(code = HttpStatus.OK)
    public String logOut(HttpSession session) {
        session.invalidate();
        return "Have a nice day";
    }

    @PutMapping("/vehicle/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void getCar(@PathVariable int id) {
        driverService.getVehicle(id);
    }

    @PutMapping("/vehicle/releaseCar")
    @ResponseStatus(code = HttpStatus.OK)
    public String releaseCar() {
        driverService.releaseVehicle();
        return "Done";
    }

    @GetMapping("/getAllDrivers")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DriverSimpleResponseDTO> getAllDrivers() {
        List<DriverSimpleResponseDTO> drivers = driverService.getAllDrivers();
        return drivers;

    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public DriverSimpleResponseDTO getDriver(@PathVariable int id) {
        DriverSimpleResponseDTO driver = driverService.getDriverById(id);
        return driver;
    }

    @PutMapping("/requestPaidLeave")
    @ResponseStatus(code = HttpStatus.OK)
    public String requestPaidLeave(@RequestBody Date start, Date end, String description) {

        //TODO
        //  driverService.requestPaidLeave(start, end, description);
        return null;
    }


    @PutMapping("driver/edit")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<EmployeeSimpleResponseDTO> editProfile(@RequestBody EmployeeProfileChangeDTO employeeProfileChangeDTO, HttpServletRequest request) {
        Object id = request.getSession().getAttribute(Constants.USER_ID);
        EmployeeSimpleResponseDTO dto = driverService.editProfile(id, employeeProfileChangeDTO);
        return ResponseEntity.status(200).body(dto);
    }


}
