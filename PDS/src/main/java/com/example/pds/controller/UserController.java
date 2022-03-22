package com.example.pds.controller;

import com.example.pds.model.user.RegisterDTO;
import com.example.pds.model.user.User;
import com.example.pds.model.user.UserService;
import com.example.pds.model.user.UserSimpleResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("users/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<UserSimpleResponseDTO> register(@RequestBody RegisterDTO registerDTO){
        UserSimpleResponseDTO dto = userService.register(registerDTO);
        return ResponseEntity.status(201).body(dto);
    }
    @PostMapping("users/login")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<UserSimpleResponseDTO> logIn(@RequestBody User user, HttpServletRequest request){
        UserSimpleResponseDTO dto = userService.login(user);
        HttpSession session = request.getSession();
        session.setAttribute("LOGGED",true);
        return ResponseEntity.status(200).body(dto);
    }

    @PostMapping("users/logout")
    @ResponseStatus(code = HttpStatus.OK)
    public String logOut(HttpSession session){
        session.invalidate();
        return "Have a nice day";
    }








}
