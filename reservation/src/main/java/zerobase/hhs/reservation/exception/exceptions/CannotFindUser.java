package zerobase.hhs.reservation.exception.exceptions;

import zerobase.hhs.reservation.exception.BusinessException;
import zerobase.hhs.reservation.exception.ExceptionsType;

public class CannotFindUser extends BusinessException {
    public CannotFindUser(ExceptionsType exceptionType) {
        super(exceptionType);
    }
}
