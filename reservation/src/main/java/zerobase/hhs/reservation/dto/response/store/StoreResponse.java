package zerobase.hhs.reservation.dto.response.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.hhs.reservation.domain.Store;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String storeName;
    private String storeAddress;
    private String storePhone;
    private String storeDescription;
    private Long maxCapacity;

    public static List<StoreResponse> list(List<Store> stores){
        return stores.stream().map(i -> new StoreResponse(
                i.getName(),
                i.getAddress(),
                i.getPhoneNumber(),
                i.getDescription(),
                i.getMaxCapacity()
        )).collect(Collectors.toList(
        ));
    }
}
