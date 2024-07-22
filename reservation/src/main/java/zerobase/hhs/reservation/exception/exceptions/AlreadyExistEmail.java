package zerobase.hhs.reservation.exception.exceptions;

import zerobase.hhs.reservation.exception.BusinessException;
import zerobase.hhs.reservation.exception.ExceptionsType;

public class AlreadyExistEmail extends BusinessException {
    public AlreadyExistEmail(ExceptionsType exceptionType) {
        super(exceptionType);
    }
}
