package zerobase.hhs.reservation.exception.exceptions;

import zerobase.hhs.reservation.exception.BusinessException;
import zerobase.hhs.reservation.exception.ExceptionsType;

public class CannotFindUserByEmail extends BusinessException {
    public CannotFindUserByEmail(ExceptionsType exceptionType) {
        super(exceptionType);
    }
}
