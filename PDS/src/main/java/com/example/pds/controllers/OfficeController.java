package com.example.pds.controllers;

import com.example.pds.model.employees.driver.driverDTO.DriverSimpleResponseDTO;
import com.example.pds.model.offices.OfficeComplexResponseDTO;
import com.example.pds.model.offices.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/office")
public class OfficeController {
    @Autowired
    private OfficeService officeService;

    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    public List<OfficeComplexResponseDTO> getAllDrivers() {
        List<OfficeComplexResponseDTO> offices = officeService.getAllOffices();
        return offices;
    }
    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public OfficeComplexResponseDTO getDriver(@PathVariable int id) {
        OfficeComplexResponseDTO dto = officeService.getOfficeById(id);
        return dto;
    }

}
