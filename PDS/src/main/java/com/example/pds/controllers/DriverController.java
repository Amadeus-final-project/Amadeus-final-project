package com.example.pds.controllers;

import com.example.pds.model.address.AddressSimpleDTO;
import com.example.pds.model.employees.driver.DriverService;
import com.example.pds.model.employees.driver.driverDTO.DriverEditProfileDTO;
import com.example.pds.model.employees.driver.driverDTO.DriverRequestVacationDTO;
import com.example.pds.model.employees.driver.driverDTO.DriverSimpleResponseDTO;
import com.example.pds.model.packages.packageDTO.PackageDriverRelatedInformationDTO;
import com.example.pds.model.vacations.VacationSimpleInfoDTO;
import com.example.pds.model.vacations.VacationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;


@RestController
@RequestMapping("driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PutMapping("/vehicle/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void getCar(@PathVariable int id, Authentication authentication) {
        Map map = (Map) authentication.getCredentials();
        int driverID = (int) map.get("id");
        driverService.getVehicle(driverID, id);
    }

    @PutMapping("/vehicle/releaseCar")
    @ResponseStatus(code = HttpStatus.OK)
    public String releaseCar(Authentication authentication) {
        Map map = (Map) authentication.getCredentials();
        int id = (int) map.get("id");
        driverService.releaseVehicle(id);
        return "Done";
    }

    @GetMapping("/getAllDrivers")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DriverSimpleResponseDTO> getAllDrivers(Pageable page) {
        List<DriverSimpleResponseDTO> drivers = driverService.getAllDrivers(page);
        return drivers;

    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public DriverSimpleResponseDTO getDriver(@PathVariable int id) {
        DriverSimpleResponseDTO driver = driverService.getDriverById(id);
        return driver;
    }

    @PutMapping("/requestVacation")
    @ResponseStatus(code = HttpStatus.OK)
    public String requestVacation(@RequestBody DriverRequestVacationDTO dto) {
        int id = dto.getId();
        LocalDate startDate = dto.getStartDate();
        LocalDate endDate = dto.getEndDate();
        String description = dto.getDescription();
        VacationType vacationType = dto.getVacationType();

        return driverService.requestVacation(id, startDate, endDate, description, vacationType);
    }

    @PutMapping("/edit")
    @ResponseStatus(code = HttpStatus.OK)
    public void editProfile(@RequestBody DriverEditProfileDTO driverEditProfileDTO, Authentication authentication) {
        Map map = (Map) authentication.getCredentials();
        int id = (int) map.get("id");
        driverService.editProfile(id, driverEditProfileDTO);

    }

    @PostMapping("/workingAddress")
    @ResponseStatus(code = HttpStatus.OK)
    public AddressSimpleDTO workingAddress(@RequestBody AddressSimpleDTO addressSimpleDTO, Authentication authentication) {
        Map map = (Map) authentication.getCredentials();
        int id = (int) map.get("id");
        AddressSimpleDTO dto = driverService.addWorkingAddress(addressSimpleDTO, id);
        return dto;
    }

    @GetMapping("/getAllPackagesForMe")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackageDriverRelatedInformationDTO> getAllPackagesInMyCity(Authentication authentication) {
        Map map = (Map) authentication.getCredentials();
        int id = (int) map.get("id");
        List<PackageDriverRelatedInformationDTO> dto = driverService.getAllPackagesInMyCity(id);
        return dto;
    }

    @GetMapping("/viewAllVacations")
    @ResponseStatus(code = HttpStatus.OK)
    public List<VacationSimpleInfoDTO> getAllVacations(Authentication authentication) {
        Map map = (Map) authentication.getCredentials();
        int id = (int) map.get("id");
        List<VacationSimpleInfoDTO> dto = driverService.getAllMyVacations(id);
        return dto;
    }
    @PutMapping("/startWork")
    @ResponseStatus(code = HttpStatus.OK)
    public void takeAssignedPackages(@RequestBody HashSet<Integer> officesIDs, Authentication authentication) {
        Map map = (Map) authentication.getCredentials();
        int id = (int) map.get("id");
        driverService.takeAssignedPackages(officesIDs, id);
    }
    @PostMapping("/checkInOffice/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void checkInOffice(@PathVariable int id, Authentication authentication) {
        Map map = (Map) authentication.getCredentials();
        int driverID = (int) map.get("id");
        driverService.checkInOffice(id,driverID );
    }


}
