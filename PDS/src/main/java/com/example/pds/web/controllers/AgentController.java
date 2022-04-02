package com.example.pds.web.controllers;

import com.example.pds.model.dto.employeeInfoDTO.EmployeeLoginDTO;
import com.example.pds.model.dto.employeeInfoDTO.EmployeeSimpleResponseDTO;
import com.example.pds.service.AgentService;
import com.example.pds.model.dto.employeeInfoDTO.EmployeeProfileChangeDTO;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/agent")
public class AgentController {

    @Autowired
    AgentService agentService;

    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<EmployeeSimpleResponseDTO> logIn(@RequestBody EmployeeLoginDTO agent) {
        EmployeeSimpleResponseDTO dto = agentService.login(agent);
        return ResponseEntity.status(200).body(dto);
    }


    @PutMapping("/edit")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<EmployeeSimpleResponseDTO> editProfile(@RequestBody EmployeeProfileChangeDTO employeeProfileChangeDTO) {
        EmployeeSimpleResponseDTO dto = agentService.editProfile(employeeProfileChangeDTO);
        return ResponseEntity.status(200).body(dto);
    }
}
