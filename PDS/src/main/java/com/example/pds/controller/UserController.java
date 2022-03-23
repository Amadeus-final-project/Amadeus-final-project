package com.example.pds.controller;

import com.example.pds.model.user.*;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    public static final String LOGGED = "LOGGED";


    @Autowired
    private UserService userService;


    @PostMapping("users/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<UserSimpleResponseDTO> register(@RequestBody RegisterDTO registerDTO) {
        UserSimpleResponseDTO dto = userService.register(registerDTO);
        return ResponseEntity.status(201).body(dto);
    }

    @PostMapping("users/login")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<UserSimpleResponseDTO> logIn(@RequestBody User user, HttpServletRequest request) {
        UserSimpleResponseDTO dto = userService.login(user);
        HttpSession session = request.getSession();
        session.setAttribute(LOGGED, true);
        session.setAttribute(Constants.USER_ID, dto.getId());
        return ResponseEntity.status(200).body(dto);
    }

    @PostMapping("users/logout")
    @ResponseStatus(code = HttpStatus.OK)
    public String logOut(HttpSession session) {
        session.invalidate();
        return "Have a nice day";
    }

    @PutMapping("users/forgottenPassword")
    @ResponseStatus(code = HttpStatus.OK)
    public String forgottenPassword(@RequestParam String email) {
        userService.forgottenPassword(email);
        return "New password token is sent to email";
    }

    @PutMapping("users/changePassword")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<UserSimpleResponseDTO> changePassword(@RequestBody UserChangePasswordDTO changePasswordDTO, HttpServletRequest request) {
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        Object id = request.getSession().getAttribute(Constants.USER_ID);
        UserSimpleResponseDTO dto = userService.changePassword(changePasswordDTO, isLogged, id);
        return ResponseEntity.status(200).body(dto);

    }

    @PutMapping("users/edit")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<UserComplexResponseDTO> editProfile(@RequestBody UserProfileChangeDTO user, HttpServletRequest request) {
        Object id = request.getSession().getAttribute(Constants.USER_ID);
        UserComplexResponseDTO dto = userService.editProfile(id, user);
        return ResponseEntity.status(200).body(dto);
    }


}
