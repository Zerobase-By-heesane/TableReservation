package zerobase.hhs.reservation.service;

import zerobase.hhs.reservation.dto.request.store.StoreRegisterRequest;
import zerobase.hhs.reservation.dto.request.store.StoreRemoveRequest;
import zerobase.hhs.reservation.dto.response.store.*;
import zerobase.hhs.reservation.dto.request.store.StoreUpdateRequest;

import java.util.List;

/**
 * Interface
 * 식당 관련된 인터페이스 정의
 * 유저와 파트너 모두 포함
 */
public interface StoreService {

    StoreListResponse getStoreList();

    StoreRegisterResponse addStore(StoreRegisterRequest request);

    StoreUpdateResponse updateStore(StoreUpdateRequest request);

    StoreRemoveResponse removeStore(StoreRemoveRequest request);
}
