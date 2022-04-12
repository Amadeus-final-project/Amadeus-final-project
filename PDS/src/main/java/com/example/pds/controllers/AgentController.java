package com.example.pds.controllers;

import com.example.pds.model.employees.agent.AgentService;
import com.example.pds.model.employees.agent.agentDTO.AgentEditProfileDTO;
import com.example.pds.model.employees.agent.agentDTO.AgentRequestVacationDTO;
import com.example.pds.model.vacations.VacationSimpleInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agent")
public class AgentController {

    @Autowired
    AgentService agentService;
    @PutMapping("/edit")
    @ResponseStatus(code = HttpStatus.OK)
    public void editProfile(@RequestBody AgentEditProfileDTO agentEditProfileDTO, Authentication authentication) {
        Map map=(Map) authentication.getCredentials();
        int id =(int) map.get("id");
        agentService.editProfile(id, agentEditProfileDTO);
    }
    @PutMapping("/approvePackage/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void approvePackage(@PathVariable int id ){
        agentService.approvePackage(id);
    }

    @PutMapping("/disapprovePackage/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void disapprovePackage(@PathVariable int id){
        agentService.disapprovePackage(id);
    }



    @PutMapping("/requestVacation")
    @ResponseStatus(code = HttpStatus.OK)
    public String requestVacation(@RequestBody AgentRequestVacationDTO dto, Authentication authentication) {

        Map map=(Map) authentication.getCredentials();
        int agentID =(int) map.get("id");

        return agentService.requestVacation(agentID, dto);
    }


    @GetMapping("/viewAllVacations")
    @ResponseStatus(code = HttpStatus.OK)
    public List<VacationSimpleInfoDTO> getAllVacations(Authentication authentication) {
        Map map = (Map) authentication.getCredentials();
        int id = (int) map.get("id");
        List<VacationSimpleInfoDTO> dto = agentService.getAllMyVacations(id);
        return dto;
    }
}
