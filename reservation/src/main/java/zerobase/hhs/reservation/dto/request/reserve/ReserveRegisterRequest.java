package zerobase.hhs.reservation.dto.request.reserve;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class ReserveRegisterRequest extends ReserveRequest{
    private final int people;
    private final LocalDateTime reserveTime;

    public ReserveRegisterRequest(
            @NotNull @Min(1) Long storeId,
            @NotNull @Min(1) Long userId,
            @Min(1) int people,
            @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") LocalDateTime reserveTime
    ) {
        super(storeId, userId);
        this.people = people;
        this.reserveTime = reserveTime;
    }
}
