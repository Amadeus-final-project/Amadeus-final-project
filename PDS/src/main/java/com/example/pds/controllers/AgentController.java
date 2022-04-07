package com.example.pds.controllers;

import com.example.pds.model.employees.agent.AgentService;
import com.example.pds.model.employees.agent.agentDTO.AgentEditProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
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
}
