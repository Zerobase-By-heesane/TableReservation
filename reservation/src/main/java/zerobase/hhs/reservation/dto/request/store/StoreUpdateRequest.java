package zerobase.hhs.reservation.dto.request.store;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreUpdateRequest{

    // 가게 ID
    @NotNull
    private Long storeId;

    // 가게 이름
    @NotBlank
    private String storeName;

    // 가게 주소
    @NotBlank
    private String storeAddress;

    // 가게 전화번호
    @NotBlank
    private String storePn;

    // 가게 설명
    @NotBlank
    private String storeDesc;

    // 가게 사장 이름
    @NotBlank
    private String storeMN;

    // 가게 최대 수용인원
    @NotNull
    @Min(1)
    private Long storeCA;

}