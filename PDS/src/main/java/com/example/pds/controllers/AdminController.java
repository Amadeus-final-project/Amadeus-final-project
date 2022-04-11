package com.example.pds.controllers;

import com.example.pds.model.employees.admin.AdminService;
import com.example.pds.model.employees.agent.AgentProfile;
import com.example.pds.model.employees.agent.agentDTO.AgentRegisterDTO;
import com.example.pds.model.employees.driver.DriverProfile;
import com.example.pds.model.employees.driver.driverDTO.DriverRegisterDTO;
import com.example.pds.model.vacations.VacationInformationDTO;
import com.example.pds.model.vehicle.VehicleComplexDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @PostMapping("/addDriver")
    @ResponseStatus(code = HttpStatus.OK)
    public DriverProfile addDriver(@RequestBody DriverRegisterDTO driverRegisterDTO) {
        DriverProfile dto = adminService.addDriver(driverRegisterDTO);
        return dto;
    }

    @PostMapping("/addAgent")
    @ResponseStatus(code = HttpStatus.OK)
    public AgentProfile addAgent(@RequestBody AgentRegisterDTO agentRegisterDTO) {
        AgentProfile dto = adminService.addAgent(agentRegisterDTO);
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

    @GetMapping("/viewVacations")
    @ResponseStatus(code = HttpStatus.OK)
    public List<VacationInformationDTO> viewVacations(Pageable page) {
        return adminService.getAllUnapprovedVacations(page);
    }
}
