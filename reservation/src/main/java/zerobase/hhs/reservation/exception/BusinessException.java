package zerobase.hhs.reservation.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final ExceptionsType exceptionType;

    public BusinessException(ExceptionsType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }
}
