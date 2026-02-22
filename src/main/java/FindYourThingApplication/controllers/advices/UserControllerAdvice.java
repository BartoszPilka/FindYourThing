package FindYourThingApplication.controllers.advices;

import FindYourThingApplication.ErrorResponse;
import FindYourThingApplication.controllers.UserController;
import FindYourThingApplication.exceptions.user.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice(basePackageClasses = UserController.class)
public class UserControllerAdvice
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException e) {

        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        message,
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordMismatch(PasswordMismatchException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(NotUniqueFieldException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedNickname(NotUniqueFieldException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(DuplicatedPasswordException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedPassword(DuplicatedPasswordException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleIntegrityViolation(DataIntegrityViolationException e)
    {
        String message = "";
        if(e.getMessage().contains("users_email_key"))
            message = "Email must be unique";
        else if(e.getMessage().contains("users_nickname_key"))
            message = "Nickname must be unique";
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        message,
                        LocalDateTime.now()
                ));
    }
}
