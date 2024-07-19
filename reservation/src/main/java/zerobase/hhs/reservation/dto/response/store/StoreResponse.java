package zerobase.hhs.reservation.dto.response.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import zerobase.hhs.reservation.domain.Store;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class StoreResponse {

    private final String storeName;
    private final String storeAddress;
    private final String storePhone;
    private final String storeDescription;
    private final Long maxCapacity;

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
