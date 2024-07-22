package zerobase.hhs.reservation.dto.request.reserve;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReserveListRequest extends ReserveRequest{
    public ReserveListRequest(@NotNull @Min(1) Long reserveId, @NotNull @Min(1) Long userId) {
        super(reserveId, userId);
    }
}
