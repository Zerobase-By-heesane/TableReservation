package zerobase.hhs.reservation.dto.request.store;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreRemoveRequest {

    @NotNull
    private Long storeId;
}
