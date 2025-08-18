package org.cardGGaduekMainService.exception;

import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomApiResponse<Void>> handleCustomException(CustomException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus()).body(CustomApiResponse.error(errorCode));
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
