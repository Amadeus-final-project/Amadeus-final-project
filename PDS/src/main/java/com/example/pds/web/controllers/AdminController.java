package com.example.pds.web.controllers;

import com.example.pds.model.dto.employeeInfoDTO.EmployeeLoginDTO;
import com.example.pds.model.dto.employeeInfoDTO.EmployeeSimpleResponseDTO;
import com.example.pds.service.AdminService;
import com.example.pds.model.dto.AgentRegisterDTO;
import com.example.pds.model.dto.driverDTO.DriverRegisterDTO;
import com.example.pds.model.dto.vehicleDTO.VehicleComplexDTO;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @DeleteMapping("/vehicle{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public String deleteVehicleByID(@PathVariable int id) {
        adminService.removeVehicle(id);
        return "Success";
    }

    @PostMapping("/addVehicle")
    @ResponseStatus(code = HttpStatus.OK)
    public VehicleComplexDTO addVehicle(@RequestBody VehicleComplexDTO vehicleComplexDTO) {
        VehicleComplexDTO dto = adminService.addVehicle(vehicleComplexDTO);
        return dto;
    }

    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.OK)
    public EmployeeSimpleResponseDTO adminLogin(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        EmployeeSimpleResponseDTO dto = adminService.loginAdmin(employeeLoginDTO);
        return dto;
    }

    @PostMapping("/addDriver")
    @ResponseStatus(code = HttpStatus.OK)
    public EmployeeSimpleResponseDTO addDriver(@RequestBody DriverRegisterDTO driverRegisterDTO) {
        EmployeeSimpleResponseDTO dto = adminService.addDriver(driverRegisterDTO);
        return dto;
    }

    @PostMapping("/addAgent")
    @ResponseStatus(code = HttpStatus.OK)
    public EmployeeSimpleResponseDTO addAgent(@RequestBody AgentRegisterDTO agentRegisterDTO) {
        EmployeeSimpleResponseDTO dto = adminService.addAgent(agentRegisterDTO);
        return dto;
    }

    @DeleteMapping(value = "/driver/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public String deleteDriverByID(@PathVariable int id) {
        adminService.removeDriver(id);
        return "Success";
    }

    @DeleteMapping("/agent/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public String deleteAgentByID(@PathVariable int id) {
        adminService.removeAgent(id);
        return "Success";
    }


}
