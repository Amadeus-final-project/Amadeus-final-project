package com.example.pds.config;

import com.example.pds.model.employees.EmployeeLoginDTO;
import com.example.pds.util.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Configuration
public class CheckViolations {


    public static <T> void check(Validator validator, T type) {
        Set<ConstraintViolation<T>> violations = validator.validate(type);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<T> violation : violations) {
                throw new BadRequestException(violation.getMessage());
            }
        }
    }
}
