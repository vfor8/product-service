package com.aweshop.products.upload.error;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductApplicationException.class)
    public final ResponseEntity<Object> handleProductApplicationException(ProductApplicationException ex, WebRequest request) {

        String logMsg = "Exception thrown when processing request, returning status " + ex.getHttpStatus();
        if (ex.getHttpStatus().is5xxServerError()) {
            log.error(logMsg, ex);
        } else {
            if (log.isDebugEnabled()) {
                log.debug(logMsg, ex);
            }
        }

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ex.getMessage());
    }
}
