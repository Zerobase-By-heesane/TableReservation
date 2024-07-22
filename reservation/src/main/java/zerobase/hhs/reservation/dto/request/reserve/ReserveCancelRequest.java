package zerobase.hhs.reservation.dto.request.reserve;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReserveCancelRequest {

    @NotNull
    @Min(1)
    private Long reserveId;

    @NotNull
    @Min(1)
    private Long userId;
}
