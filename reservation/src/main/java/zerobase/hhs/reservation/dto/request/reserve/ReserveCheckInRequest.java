package zerobase.hhs.reservation.dto.request.reserve;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReserveCheckInRequest {

    @NotNull
    @Min(1)
    private Long reserveId;

    @NotNull
    @Min(1)
    private Long userId;
}
