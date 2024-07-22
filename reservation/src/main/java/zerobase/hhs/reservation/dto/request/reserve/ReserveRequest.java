package zerobase.hhs.reservation.dto.request.reserve;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReserveRequest {

    @Min(1)
    private Long storeId;

    @Min(1)
    private Long userId;
}
