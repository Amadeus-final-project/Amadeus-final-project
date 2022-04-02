package com.example.pds.web.controllers;


import com.example.pds.model.packages.*;
import com.example.pds.model.user.User;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/package")
public class PackageController {
    @Autowired
    PackageService packageService;

    @PostMapping("/send")
    @ResponseStatus(code = HttpStatus.OK)
    public PackageSimpleResponseDTO sendPackage(@RequestBody SendPackageDTO sendPackageDTO, HttpServletRequest request) {
        Object id = request.getSession().getAttribute(Constants.USER_ID);
        Object isUser = request.getSession().getAttribute(Constants.IS_USER);
        PackageSimpleResponseDTO dto = packageService.sendPackage(id, isUser, sendPackageDTO);
        return dto;
    }

    @GetMapping("/getAllPackages")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackageComplexResponseDTO> getAllPackages(Authentication authentication) {
        var test = authentication.getCredentials();
        List<PackageComplexResponseDTO> packages = packageService.getAllPackages(false, false, true);
        return packages;

    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PackageComplexResponseDTO getPackageById(@PathVariable int id, HttpServletRequest request) {
        Object isAdmin = request.getSession().getAttribute(Constants.IS_ADMIN);
        Object isAgent = request.getSession().getAttribute(Constants.IS_AGENT);
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        PackageComplexResponseDTO packageDto = packageService.getPackage(id, isAdmin, isAgent, isLogged);
        return packageDto;
    }

    @GetMapping("/getPendingPackages")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackageGetMyPackagesDTO> getAllPendingPackages(HttpServletRequest request){
        Object isAgent = request.getSession().getAttribute(Constants.IS_AGENT);
        Object isAdmin = request.getSession().getAttribute(Constants.IS_ADMIN);
        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
        List<PackageGetMyPackagesDTO> dto = packageService.getAllPendingPackages(isAdmin, isAgent, isLogged);
        return dto;

    }

}
