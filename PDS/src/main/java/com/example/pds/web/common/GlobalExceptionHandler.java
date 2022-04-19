package com.example.pds.web.common;

import com.example.pds.util.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    private class DefaultExceptionPayload{
//        private String message;
//
//        public String getMessage() {
//            return message;
//        }
//
//        public void setMessage(String message) {
//            this.message = message;
//        }
//
//        public DefaultExceptionPayload() {
//        }
//
//
//    }
//
//    @ExceptionHandler(value = {BadRequestException.class})
//    public ResponseEntity<Object> handleBadRequest(RuntimeException runtimeException){
//
//
//        return new ResponseEntity<>(new DefaultExceptionPayload(){{setMessage(runtimeException.getMessage());}}, HttpStatus.BAD_REQUEST);
//    }

//    @ExceptionHandler(value = {Exception.class})
//    public ResponseEntity<Object> handleInternalException(RuntimeException runtimeException){
//
//        //TODO disable for prod
//        return new ResponseEntity<>(new DefaultExceptionPayload(){{setMessage(runtimeException.getMessage());}}, HttpStatus.INTERNAL_SERVER_ERROR);
//    }


}
