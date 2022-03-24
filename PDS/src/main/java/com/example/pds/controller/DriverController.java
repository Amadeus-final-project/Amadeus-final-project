package com.example.pds.controller;

import com.example.pds.model.employees.EmployeeLoginDTO;
import com.example.pds.model.employees.EmployeeSimpleResponseDTO;
import com.example.pds.model.employees.driver.DriverService;
import com.example.pds.model.user.userDTO.LoginDTO;
import com.example.pds.model.user.userDTO.UserSimpleResponseDTO;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("driver/login")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<EmployeeSimpleResponseDTO> logIn(@RequestBody EmployeeLoginDTO driver, HttpServletRequest request) {
        EmployeeSimpleResponseDTO dto = driverService.login(driver);
        HttpSession session = request.getSession();
        session.setAttribute(Constants.LOGGED, true);
        session.setAttribute(Constants.USER_ID, dto.getId());
        return ResponseEntity.status(200).body(dto);
    }
}
