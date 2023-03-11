package com.fujitsu.feeCalculator.REST;

import com.fujitsu.feeCalculator.Exceptions.BusinessRuleAlreadyImplementedException;
import com.fujitsu.feeCalculator.Exceptions.CityNotFoundException;
import com.fujitsu.feeCalculator.Exceptions.VehicleNotFoundException;
import com.fujitsu.feeCalculator.REST.DataClasses.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            CityNotFoundException.class,
            VehicleNotFoundException.class,
            BusinessRuleAlreadyImplementedException.class
    })
    public ResponseEntity<CustomErrorResponse> customHandleNotFound(Exception ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

    }


}
