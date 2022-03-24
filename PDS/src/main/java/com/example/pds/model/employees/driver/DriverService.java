package com.example.pds.model.employees.driver;

import com.example.pds.model.employees.EmployeeLoginDTO;
import com.example.pds.model.employees.EmployeeSimpleResponseDTO;
import com.example.pds.model.user.User;
import com.example.pds.model.user.UserRepository;
import com.example.pds.model.user.userDTO.RegisterDTO;
import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Validator validator;

    public EmployeeSimpleResponseDTO login(EmployeeLoginDTO login) {

        Set<ConstraintViolation<EmployeeLoginDTO>> violations = validator.validate(login);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<EmployeeLoginDTO> violation : violations) {
                throw new BadRequestException(violation.getMessage());
            }
        }

        Driver driver = driverRepository.findByEmail(login.getEmail());
        if (driver == null) {
            throw new NotFoundException("Driver not found");
        }
        //if (!passwordEncoder.matches(login.getPassword(), driver.getPassword())) {
          //  throw new BadRequestException("Wrong credentials");
        //}
        return modelMapper.map(driver,EmployeeSimpleResponseDTO.class);
    }
}
