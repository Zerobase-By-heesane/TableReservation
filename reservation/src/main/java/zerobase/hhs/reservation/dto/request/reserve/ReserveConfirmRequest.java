package zerobase.hhs.reservation.dto.request.reserve;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReserveConfirmRequest extends ReserveRequest {

    private final Long reserveId;
    public ReserveConfirmRequest(@NotNull @Min(1) Long storeId, @NotNull @Min(1) Long userId, @Min(1) Long reserveId) {
        super(storeId, userId);
        this.reserveId = reserveId;
    }
}
