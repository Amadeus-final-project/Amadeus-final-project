package com.example.pds.web.controllers;

import com.example.pds.model.employees.EmployeeLoginDTO;
import com.example.pds.model.employees.EmployeeSimpleResponseDTO;
import com.example.pds.model.employees.agent.AgentService;
import com.example.pds.model.employees.employeeInfo.EmployeeProfileChangeDTO;
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
    public ResponseEntity<EmployeeSimpleResponseDTO> logIn(@RequestBody EmployeeLoginDTO agent, HttpServletRequest request) {
        EmployeeSimpleResponseDTO dto = agentService.login(agent);
        HttpSession session = request.getSession();
        session.setAttribute(Constants.LOGGED, true);
        session.setAttribute(Constants.USER_ID, dto.getId());
        session.setAttribute(Constants.IS_AGENT, true);
        return ResponseEntity.status(200).body(dto);
    }


    @PutMapping("/edit")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<EmployeeSimpleResponseDTO> editProfile(@RequestBody EmployeeProfileChangeDTO employeeProfileChangeDTO, HttpServletRequest request) {
        Object id = request.getSession().getAttribute(Constants.USER_ID);
        Object isAgent = request.getSession().getAttribute(Constants.IS_AGENT);
        EmployeeSimpleResponseDTO dto = agentService.editProfile(id, employeeProfileChangeDTO, isAgent);
        return ResponseEntity.status(200).body(dto);
    }
}
