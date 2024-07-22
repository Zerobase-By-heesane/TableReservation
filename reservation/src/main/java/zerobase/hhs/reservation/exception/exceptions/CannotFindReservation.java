package zerobase.hhs.reservation.exception.exceptions;

import zerobase.hhs.reservation.exception.BusinessException;
import zerobase.hhs.reservation.exception.ExceptionsType;

public class CannotFindReservation extends BusinessException {
    public CannotFindReservation(ExceptionsType exceptionType) {
        super(exceptionType);
    }
}
