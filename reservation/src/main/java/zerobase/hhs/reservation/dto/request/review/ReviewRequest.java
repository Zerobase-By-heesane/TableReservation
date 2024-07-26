package zerobase.hhs.reservation.dto.request.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewRequest {

    @Min(1) @Max(5)
    private int fulfillment;

    @NotBlank @NotNull
    private String content;

    @Min(1) @NotNull
    private Long reservationId;
}
