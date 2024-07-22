package zerobase.hhs.reservation.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zerobase.hhs.reservation.exception.exceptions.CannotFindReservation;
import zerobase.hhs.reservation.exception.exceptions.CannotFindStore;
import zerobase.hhs.reservation.exception.exceptions.CannotFindUser;
import zerobase.hhs.reservation.exception.exceptions.CannotFindUserByEmail;

@RestControllerAdvice
public class GlobalControllerAdvise {

    @ExceptionHandler(CannotFindUserByEmail.class)
    public ResponseEntity<String> handleValidationException(CannotFindUserByEmail e) {
        return ResponseEntity.status(e.getExceptionType().getStatus()).body(e.getExceptionType().getMessage());
    }

    @ExceptionHandler(CannotFindUser.class)
    public ResponseEntity<String> handleValidationException(CannotFindUser e) {
        return ResponseEntity.status(e.getExceptionType().getStatus()).body(e.getExceptionType().getMessage());
    }

    @ExceptionHandler(CannotFindReservation.class)
    public ResponseEntity<String> handleValidationException(CannotFindReservation e) {
        return ResponseEntity.status(e.getExceptionType().getStatus()).body(e.getExceptionType().getMessage());
    }

    @ExceptionHandler(CannotFindStore.class)
    public ResponseEntity<String> handleValidationException(CannotFindStore e) {
        return ResponseEntity.status(e.getExceptionType().getStatus()).body(e.getExceptionType().getMessage());
    }
}
