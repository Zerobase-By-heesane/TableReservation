package zerobase.hhs.reservation.dto.response.store;

import lombok.Getter;

import java.util.List;

@Getter
public class StoreListResponse {
    List<StoreResponse> storeList;

    public StoreListResponse(List<StoreResponse> storeList){
        this.storeList = storeList;
    }
}
