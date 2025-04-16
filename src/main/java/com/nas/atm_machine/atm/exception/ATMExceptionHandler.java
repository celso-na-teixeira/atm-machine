package com.nas.atm_machine.atm.exception;

import com.nas.atm_machine.error.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ATMExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn(ex.getMessage());
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex));
    }


    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(ATMOutOfCashException.class)
    protected ResponseEntity<Object> handleATMOutOfCash(
            ATMOutOfCashException ex) {
        log.warn(ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.SERVICE_UNAVAILABLE, ATMOutOfCashException.shortMessage, ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({InsufficientFundsException.class, ATMException.class})
    protected ResponseEntity<Object> handleATMOutOfCash(
            RuntimeException ex) {
        log.warn(ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ATMOutOfCashException.shortMessage, ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler()
    protected ResponseEntity<Object> handleUnexpectedException(
            RuntimeException ex) {
        log.error("Unexpected error during withdrawal", ex);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        return buildResponseEntity(apiError);
    }
}
