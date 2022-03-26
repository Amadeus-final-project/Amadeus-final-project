package com.example.pds.controller;

import com.example.pds.model.employees.EmployeeLoginDTO;
import com.example.pds.model.employees.EmployeeSimpleResponseDTO;
import com.example.pds.model.employees.admin.AdminService;
import com.example.pds.model.employees.agent.agentDTO.AgentRegisterDTO;
import com.example.pds.model.employees.driver.driverDTO.DriverRegisterDTO;
import com.example.pds.model.vehicle.VehicleComplexDTO;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AdminController {
    @Autowired
    AdminService adminService;

    @DeleteMapping("admin/vehicle{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public String deleteVehicleByID(@PathVariable int id, HttpServletRequest request) {
        Object isAdmin = request.getSession().getAttribute(Constants.IS_ADMIN);
        adminService.removeVehicle(id, isAdmin);
        return "Success";
    }

    @PostMapping("admin/addVehicle")
    @ResponseStatus(code = HttpStatus.OK)
    public VehicleComplexDTO addVehicle(@RequestBody VehicleComplexDTO vehicleComplexDTO, HttpServletRequest request) {
        Object isAdmin = request.getSession().getAttribute(Constants.IS_ADMIN);
        VehicleComplexDTO dto = adminService.addVehicle(isAdmin, vehicleComplexDTO);
        return dto;
    }

    @PostMapping("admin/login")
    @ResponseStatus(code = HttpStatus.OK)
    public EmployeeSimpleResponseDTO adminLogin(@RequestBody EmployeeLoginDTO employeeLoginDTO, HttpServletRequest request) {
        EmployeeSimpleResponseDTO dto = adminService.loginAdmin(employeeLoginDTO);
        HttpSession session = request.getSession();
        session.setAttribute(Constants.LOGGED, true);
        session.setAttribute(Constants.USER_ID, dto.getId());
        session.setAttribute(Constants.IS_ADMIN, true);
        return dto;
    }

    @PostMapping("admin/addDriver")
    @ResponseStatus(code = HttpStatus.OK)
    public EmployeeSimpleResponseDTO addDriver(@RequestBody DriverRegisterDTO driverRegisterDTO, HttpServletRequest request) {
        Object isAdmin = request.getSession().getAttribute(Constants.IS_ADMIN);
        EmployeeSimpleResponseDTO dto = adminService.addDriver(isAdmin, driverRegisterDTO);
        return dto;
    }

    @PostMapping("admin/addAgent")
    @ResponseStatus(code = HttpStatus.OK)
    public EmployeeSimpleResponseDTO addAgent(@RequestBody AgentRegisterDTO agentRegisterDTO, HttpServletRequest request) {
        Object isAdmin = request.getSession().getAttribute(Constants.IS_ADMIN);
        EmployeeSimpleResponseDTO dto = adminService.addAgent(isAdmin, agentRegisterDTO);
        return dto;
    }

    @DeleteMapping(value = "admin/driver/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public String deleteDriverByID(@PathVariable int id, HttpServletRequest request) {
        Object isAdmin = request.getSession().getAttribute(Constants.IS_ADMIN);
        adminService.removeDriver(id, isAdmin);
        return "Success";
    }

    @DeleteMapping("admin/agent/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public String deleteAgentByID(@PathVariable int id, HttpServletRequest request) {
        Object isAdmin = request.getSession().getAttribute(Constants.IS_ADMIN);
        adminService.removeAgent(id, isAdmin);
        return "Success";
    }


}
