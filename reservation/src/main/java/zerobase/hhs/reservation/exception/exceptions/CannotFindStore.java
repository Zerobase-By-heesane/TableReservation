package zerobase.hhs.reservation.exception.exceptions;

import zerobase.hhs.reservation.exception.BusinessException;
import zerobase.hhs.reservation.exception.ExceptionsType;

public class CannotFindStore extends BusinessException {
    public CannotFindStore(ExceptionsType exceptionType) {
        super(exceptionType);
    }
}
