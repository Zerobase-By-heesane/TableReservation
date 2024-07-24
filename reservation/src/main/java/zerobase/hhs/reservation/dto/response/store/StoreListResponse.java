package zerobase.hhs.reservation.dto.response.store;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
public class StoreListResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    List<StoreResponse> storeList;

    public StoreListResponse(List<StoreResponse> storeList){
        this.storeList = storeList;
    }
}
