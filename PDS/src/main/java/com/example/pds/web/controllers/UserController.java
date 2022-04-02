package com.example.pds.web.controllers;

import com.example.pds.model.dto.packagesDTO.PackageGetMyPackagesDTO;
import com.example.pds.model.dto.userDTO.*;
import com.example.pds.model.dto.TransactionResponseDTO;
import com.example.pds.service.UserService;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;

     @GetMapping("/login")
     public String login() {
         return "/profile";
     }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest) {
         httpServletRequest.getSession().invalidate();
        return "redirect:/";
    }


//    @PostMapping("/register")
//    @ResponseStatus(code = HttpStatus.CREATED)
//    public String registerConfirm(User user) {
//        return "redirect:/login";
//    }


    @PostMapping("/register")
    public String register(@RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO);
        return "redirect:/login";
    }


//    @RequestMapping(value= "/register", method = RequestMethod.GET)
//        public String login () {
//            return "register";
//        }
//
//    @PostMapping("/login")
//    public String login(@RequestBody LoginDTO user, HttpServletRequest request) {
//        UserSimpleResponseDTO dto = userService.login(user);
//        HttpSession session = request.getSession();
//        session.setAttribute(Constants.IS_USER, true);
//        session.setAttribute(Constants.LOGGED, true);
//        session.setAttribute(Constants.USER_ID, dto.getId());
//        return "/";
//    }

//    @PostMapping("/logout")
//    @ResponseStatus(code = HttpStatus.OK)
//    public String logOut(HttpSession session) {
//        session.invalidate();
//        return "Have a nice day";
//    }

    @PutMapping("/forgottenPassword")
    @ResponseStatus(code = HttpStatus.OK)
    public String forgottenPassword(@RequestParam String email) {
        userService.forgottenPassword(email);
        return "New password token is sent to email";
    }

    @PutMapping("/changePassword")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<UserSimpleResponseDTO> changePassword(@RequestBody UserChangePasswordDTO changePasswordDTO, HttpServletRequest request) {
        Object id = request.getSession().getAttribute(Constants.USER_ID);
        UserSimpleResponseDTO dto = userService.changePassword(changePasswordDTO, id);
        return ResponseEntity.status(200).body(dto);

    }

    @PutMapping("/edit")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<UserComplexResponseDTO> editProfile(@RequestBody UserProfileChangeDTO user, HttpServletRequest request) {
        Object id = request.getSession().getAttribute(Constants.USER_ID);
        UserComplexResponseDTO dto = userService.editProfile(id, user);
        return ResponseEntity.status(200).body(dto);
    }

    @GetMapping("/getAllPackages")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackageGetMyPackagesDTO> getAllPackages(HttpServletRequest request) {
        Object id = request.getSession().getAttribute(Constants.USER_ID);
        List<PackageGetMyPackagesDTO> dtoList = userService.getAllPackages(id);
        return dtoList;
    }

    @GetMapping("/getPackageById/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PackageGetMyPackagesDTO getPackageById(@PathVariable int id, HttpServletRequest request) {
        Object userId = request.getSession().getAttribute(Constants.USER_ID);
        PackageGetMyPackagesDTO dto = userService.getPackageBydId(id, userId);
        return dto;
    }

    @GetMapping("/getAllTransactions")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TransactionResponseDTO> getAllTransactions(HttpServletRequest request) {
        Object id = request.getSession().getAttribute(Constants.USER_ID);
        List<TransactionResponseDTO> transactions = userService.getAllTransactions(id);
        return transactions;
    }

}
