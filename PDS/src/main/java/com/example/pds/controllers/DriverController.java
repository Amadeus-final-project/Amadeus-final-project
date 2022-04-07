package com.example.pds.controllers;

import com.example.pds.model.employees.EmployeeLoginDTO;
import com.example.pds.model.employees.driver.DriverProfile;
import com.example.pds.model.employees.driver.DriverService;
import com.example.pds.model.employees.driver.driverDTO.DriverSimpleResponseDTO;
import com.example.pds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

//    @PostMapping("/login")
//    @ResponseStatus(code = HttpStatus.OK)
//    public ResponseEntity<DriverProfile> logIn(@RequestBody EmployeeLoginDTO driver, HttpServletRequest request) {
//        DriverProfile dto = driverService.login(driver);
//        HttpSession session = request.getSession();
//        session.setAttribute(Constants.LOGGED, true);
//        session.setAttribute(Constants.USER_ID, dto.getId());
//        session.setAttribute(Constants.IS_DRIVER, true);
//        return ResponseEntity.status(200).body(dto);
//    }

//    @PostMapping("/logout")
//    @ResponseStatus(code = HttpStatus.OK)
//    public String logOut(HttpSession session) {
//        session.invalidate();
//        return "Have a nice day";
//    }

    @PutMapping("/vehicle/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void getCar(@PathVariable int id, Authentication authentication) {
        Map map=(Map) authentication.getCredentials();
        int driverID =(int) map.get("id");
        driverService.getVehicle(driverID, id);
    }

    @PutMapping("/vehicle/releaseCar")
    @ResponseStatus(code = HttpStatus.OK)
    public String releaseCar(Authentication authentication) {
        Map map=(Map) authentication.getCredentials();
        int id =(int) map.get("id");
        driverService.releaseVehicle(id);
        return "Done";
    }

    @GetMapping("/getAllDrivers")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DriverSimpleResponseDTO> getAllDrivers() {
        List<DriverSimpleResponseDTO> drivers = driverService.getAllDrivers();
        return drivers;

    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public DriverSimpleResponseDTO getDriver(@PathVariable int id) {
        DriverSimpleResponseDTO driver = driverService.getDriverById(id);
        return driver;
    }

//    @PutMapping("/requestPaidLeave")
//    @ResponseStatus(code = HttpStatus.OK)
//    public String requestPaidLeave(@RequestBody Date start, Date end, String description, HttpServletRequest request) {
//        Object isUser = request.getSession().getAttribute(Constants.IS_USER);
//        Object isLogged = request.getSession().getAttribute(Constants.LOGGED);
//        //TODO
//        //  driverService.requestPaidLeave(start, end, description, isLogged, isUser);
//        return null;
//    }

//TODO redirect or idk something
//    @PutMapping("driver/edit")
//    @ResponseStatus(code = HttpStatus.OK)
//    public void editProfile(@RequestBody EmployeeProfileChangeDTO employeeProfileChangeDTO, HttpServletRequest request) {
//        Object id = request.getSession().getAttribute(Constants.USER_ID);
//        Object isDriver = request.getSession().getAttribute(Constants.IS_DRIVER);
//         driverService.editProfile(id, employeeProfileChangeDTO, isDriver);
//
//    }


}
