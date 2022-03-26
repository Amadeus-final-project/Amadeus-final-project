package com.example.pds.config;

import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.UnauthorizedException;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CheckAuthentications {


    public static void checkIfLogged(Object isLogged) {
        if (isLogged == null) {
            throw new BadRequestException("You are not logged in");
        }
    }

    public static void checkIfEmployee(Object isUser) {
        if (isUser != null) {
            throw new UnauthorizedException("You are unauthorized");
        }
    }

    public static void checkIfDriver(Object isDriver) {
        if (isDriver == null) {
            throw new BadRequestException("You are not a driver");
        }
    }

    public static void checkIfAdmin(Object isAdmin) {
        if (isAdmin == null) {
            throw new UnauthorizedException("You are unauthorized");
        }
    }

    public static void checkIfAgent(Object isAgent) {
        if (isAgent == null) {
            throw new BadRequestException("You are not an agent");
        }
    }

    public static void checkIfUser(Object isUser) {
        if (isUser == null) {
            throw new BadRequestException("You are not logged in");
        }
    }
}
