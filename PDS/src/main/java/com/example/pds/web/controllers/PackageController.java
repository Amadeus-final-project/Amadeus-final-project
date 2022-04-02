package com.example.pds.web.controllers;


import com.example.pds.model.dto.packagesDTO.PackageComplexResponseDTO;
import com.example.pds.model.dto.packagesDTO.PackageGetMyPackagesDTO;
import com.example.pds.model.dto.packagesDTO.PackageSimpleResponseDTO;
import com.example.pds.model.dto.packagesDTO.SendPackageDTO;
import com.example.pds.service.PackageService;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/package")
public class PackageController {
    @Autowired
    PackageService packageService;

    @PostMapping("/send")
    @ResponseStatus(code = HttpStatus.OK)
    public PackageSimpleResponseDTO sendPackage(@RequestBody SendPackageDTO sendPackageDTO) {
        PackageSimpleResponseDTO dto = packageService.sendPackage(sendPackageDTO);
        return dto;
    }

    @GetMapping("/getAllPackages")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackageComplexResponseDTO> getAllPackages() {
        List<PackageComplexResponseDTO> packages = packageService.getAllPackages();
        return packages;

    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PackageComplexResponseDTO getPackageById(@PathVariable int id) {

        PackageComplexResponseDTO packageDto = packageService.getPackage(id);
        return packageDto;
    }

    @GetMapping("/getPendingPackages")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackageGetMyPackagesDTO> getAllPendingPackages(){
        List<PackageGetMyPackagesDTO> dto = packageService.getAllPendingPackages();
        return dto;

    }

}
