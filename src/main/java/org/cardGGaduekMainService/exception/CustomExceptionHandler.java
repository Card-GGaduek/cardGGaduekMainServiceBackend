package org.cardGGaduekMainService.exception;

import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Log4j2
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus()).body(ApiResponse.error(errorCode));
    }

//    @ExceptionHandler(Exception.class)
//    public String except(Exception ex, Model model) {
//        log.error("Exception ......." + ex.getMessage());
//        model.addAttribute("exception", ex);
//        log.error(model);
//        return "error_page";
//    }
//
//    @ExceptionHandler(NoHandlerFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String handle404(NoHandlerFoundException ex, Model model, HttpServletRequest request) {
//        log.error(ex);
//        model.addAttribute("uri", request.getRequestURI());
//        return "custom404";
//    }

}
