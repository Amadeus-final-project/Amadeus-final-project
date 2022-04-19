package com.example.pds.controllers;


import com.example.pds.model.packages.*;
import com.example.pds.model.packages.packageDTO.PackageComplexResponseDTO;
import com.example.pds.model.packages.packageDTO.PackageGetMyPackagesDTO;
import com.example.pds.model.packages.packageDTO.PackageSimpleResponseDTO;
import com.example.pds.model.packages.packageDTO.SendPackageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/package")
public class PackageController {
    @Autowired
    PackageService packageService;

    @PostMapping("/send")
    @ResponseStatus(code = HttpStatus.OK)
    public PackageSimpleResponseDTO sendPackage(@RequestBody SendPackageDTO sendPackageDTO, Authentication authentication) {
        Map map=(Map) authentication.getCredentials();
        int id =(int) map.get("id");
        PackageSimpleResponseDTO dto = packageService.sendPackage(id, sendPackageDTO);
        return dto;
    }

    @GetMapping("/getAllPackages")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackageComplexResponseDTO> getAllPackages(Pageable page) {
        return packageService.getAllPackages(page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PackageComplexResponseDTO getPackageById(@PathVariable int id, Authentication authentication) {
        Map map=(Map) authentication.getCredentials();
        PackageComplexResponseDTO packageDto = packageService.getPackage(id);
        return packageDto;
    }

    @GetMapping("/getPendingPackages")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackageSimpleResponseDTO> getAllPendingPackages(Pageable page){
        List<PackageSimpleResponseDTO> dto = packageService.getAllPendingPackages(page);
        return dto;

    }

    @GetMapping("/getAllMyPackages")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackageSimpleResponseDTO> getAllMyPackages(Authentication authentication, Pageable page){
        Map map=(Map) authentication.getCredentials();
        int id =(int) map.get("id");
        List<PackageSimpleResponseDTO> dtoList= packageService.getAllMyPackages(id, page);
        return dtoList;
    }
    @GetMapping("/getAllPackagesSendByMe")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackageGetMyPackagesDTO> getAllPackagesSendByMe(Authentication authentication){
        Map map=(Map) authentication.getCredentials();
        int id =(int) map.get("id");
        List<PackageGetMyPackagesDTO> dtoList= packageService.getAllPackagesSendByMe(id);
        return dtoList;
    }

    @GetMapping("/getMyPackage/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PackageGetMyPackagesDTO getMyPackage(@PathVariable int id, Authentication authentication){
        Map map=(Map) authentication.getCredentials();
        int UserID =(int) map.get("id");
        PackageGetMyPackagesDTO dto = packageService.getMyPackage(id , UserID);
        return dto;
    }

    @GetMapping("/getMyPackage/{trackingNumber}")
    @ResponseStatus(code = HttpStatus.OK)
    public PackageGetMyPackagesDTO findPackageByTrackingNumber(@PathVariable String trackingNumber){
        PackageGetMyPackagesDTO dto = packageService.findPackageByTrackingNumber(trackingNumber);
        return dto;
    }

}
