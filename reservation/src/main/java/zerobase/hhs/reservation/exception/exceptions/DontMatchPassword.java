package zerobase.hhs.reservation.exception.exceptions;

import zerobase.hhs.reservation.exception.BusinessException;
import zerobase.hhs.reservation.exception.ExceptionsType;

public class DontMatchPassword extends BusinessException {
    public DontMatchPassword(ExceptionsType exceptionType) {
        super(exceptionType);
    }
}
