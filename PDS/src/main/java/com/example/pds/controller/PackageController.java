package com.example.pds.controller;


import com.example.pds.model.packages.PackageComplexResponseDTO;
import com.example.pds.model.packages.PackageService;
import com.example.pds.model.packages.PackageSimpleResponseDTO;
import com.example.pds.model.packages.SendPackageDTO;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class PackageController {
    @Autowired
    PackageService packageService;

    @PostMapping("package/send")
    @ResponseStatus(code = HttpStatus.OK)
    public PackageSimpleResponseDTO sendPackage(@RequestBody SendPackageDTO sendPackageDTO, HttpServletRequest request) {
        Object id = request.getSession().getAttribute(Constants.USER_ID);
        Object isUser = request.getSession().getAttribute(Constants.IS_USER);
        PackageSimpleResponseDTO dto = packageService.sendPackage(id, isUser, sendPackageDTO);
        return dto;
    }

    @GetMapping("package/getAllPackages")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackageComplexResponseDTO> getAllPackages(HttpServletRequest request) {
        Object isAdmin = request.getSession().getAttribute(Constants.IS_ADMIN);
        Object isAgent = request.getSession().getAttribute(Constants.IS_AGENT);
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        List<PackageComplexResponseDTO> packages = packageService.getAllPackages(isAdmin, isAgent, isLogged);
        return packages;

    }

    @GetMapping("package/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PackageComplexResponseDTO getPackageById(@PathVariable int id, HttpServletRequest request) {
        Object isAdmin = request.getSession().getAttribute(Constants.IS_ADMIN);
        Object isAgent = request.getSession().getAttribute(Constants.IS_AGENT);
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        PackageComplexResponseDTO packageDto = packageService.getPackage(id, isAdmin, isAgent, isLogged);
        return packageDto;
    }

}
