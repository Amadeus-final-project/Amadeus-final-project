package com.example.pds.web.common;

import com.example.pds.util.exceptions.BadRequestException;
import com.example.pds.util.exceptions.NotFoundException;
import com.example.pds.util.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private class DefaultExceptionPayload{
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public DefaultExceptionPayload() {
        }


    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequest(RuntimeException runtimeException){


        return new ResponseEntity<>(new DefaultExceptionPayload(){{setMessage(runtimeException.getMessage());}}, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException unauthorizedException){


        return new ResponseEntity<>(new DefaultExceptionPayload(){{setMessage(unauthorizedException.getMessage());}}, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException){


        return new ResponseEntity<>(new DefaultExceptionPayload(){{setMessage(notFoundException.getMessage());}}, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleInternalException(RuntimeException runtimeException){

        //TODO disable for prod
        return new ResponseEntity<>(new DefaultExceptionPayload(){{setMessage(runtimeException.getMessage());}}, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
