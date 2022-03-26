package com.example.pds.controller;

import com.example.pds.model.packages.PackageService;
import com.example.pds.model.packages.PackageSimpleResponseDTO;
import com.example.pds.model.packages.SendPackageDTO;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class PackageController {
    @Autowired
    PackageService packageService;

    @PostMapping("package/send")
    @ResponseStatus(code = HttpStatus.OK)
    public PackageSimpleResponseDTO sendPackage(SendPackageDTO sendPackageDTO, HttpServletRequest request){
        Object id = request.getSession().getAttribute(Constants.USER_ID);
        Object isUser = request.getSession().getAttribute(Constants.IS_USER);
        PackageSimpleResponseDTO dto = packageService.sendPackage(id, isUser, sendPackageDTO);
        return dto;
    }
}
