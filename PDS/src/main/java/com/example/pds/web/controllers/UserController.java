package com.example.pds.web.controllers;

import com.example.pds.model.packages.PackageGetMyPackagesDTO;
import com.example.pds.model.transaction.TransactionResponseDTO;
import com.example.pds.model.user.*;
import com.example.pds.model.user.userDTO.*;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<UserSimpleResponseDTO> register(@RequestBody RegisterDTO registerDTO) {
        UserSimpleResponseDTO dto = userService.register(registerDTO);
        return ResponseEntity.status(201).body(dto);
    }

    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<UserSimpleResponseDTO> logIn(@RequestBody LoginDTO user, HttpServletRequest request) {
        UserSimpleResponseDTO dto = userService.login(user);
        HttpSession session = request.getSession();
        session.setAttribute(Constants.IS_USER, true);
        session.setAttribute(Constants.LOGGED, true);
        session.setAttribute(Constants.USER_ID, dto.getId());
        return ResponseEntity.status(200).body(dto);
    }

    @PostMapping("/logout")
    @ResponseStatus(code = HttpStatus.OK)
    public String logOut(HttpSession session) {
        session.invalidate();
        return "Have a nice day";
    }

    @PutMapping("/forgottenPassword")
    @ResponseStatus(code = HttpStatus.OK)
    public String forgottenPassword(@RequestParam String email) {
        userService.forgottenPassword(email);
        return "New password token is sent to email";
    }

    @PutMapping("/changePassword")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<UserSimpleResponseDTO> changePassword(@RequestBody UserChangePasswordDTO changePasswordDTO,Authentication authentication) {
        Map map=(Map) authentication.getCredentials();
        int id =(int) map.get("id");
        UserSimpleResponseDTO dto = userService.changePassword(changePasswordDTO, id);
        return ResponseEntity.status(200).body(dto);

    }

    @PutMapping("/edit")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<UserComplexResponseDTO> editProfile(@RequestBody UserProfileChangeDTO user, Authentication authentication) {
        Map map=(Map) authentication.getCredentials();
        int id =(int) map.get("id");
        UserComplexResponseDTO dto = userService.editProfile(id, user);
        return ResponseEntity.status(200).body(dto);
    }

    @GetMapping("/getAllPackages")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackageGetMyPackagesDTO> getAllPackages( Authentication authentication) {
        Map map=(Map) authentication.getCredentials();
        int id =(int) map.get("id");
        List<PackageGetMyPackagesDTO> dtoList = userService.getAllPackages(id);
        return dtoList;
    }

    @GetMapping("/getPackageById/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PackageGetMyPackagesDTO getPackageById(@PathVariable int id,  Authentication authentication) {
        Map map=(Map) authentication.getCredentials();
        int userID =(int) map.get("id");
        PackageGetMyPackagesDTO dto = userService.getPackageBydId(id, userID);
        return dto;
    }

    @GetMapping("/getAllTransactions")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TransactionResponseDTO> getAllTransactions(Authentication authentication) {
        Map map=(Map) authentication.getCredentials();
        int id =(int) map.get("id");
        List<TransactionResponseDTO> transactions = userService.getAllTransactions(id);
        return transactions;
    }

}
