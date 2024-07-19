package zerobase.hhs.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.hhs.reservation.domain.Store;
import zerobase.hhs.reservation.domain.User;
import zerobase.hhs.reservation.dto.request.store.StoreRegisterRequest;
import zerobase.hhs.reservation.dto.request.store.StoreRemoveRequest;
import zerobase.hhs.reservation.dto.request.store.StoreUpdateRequest;
import zerobase.hhs.reservation.dto.response.store.*;
import zerobase.hhs.reservation.repository.StoreRepository;
import zerobase.hhs.reservation.repository.UserRepository;
import zerobase.hhs.reservation.service.StoreService;
import zerobase.hhs.reservation.type.ResponseType;

@Slf4j
@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Override
    public StoreListResponse getStoreList() {
        return new StoreListResponse(StoreResponse.list(storeRepository.findAllByDeletedAtIsNull()));
    }

    @Transactional
    @Override
    public StoreRegisterResponse addStore(StoreRegisterRequest request) {

        try {
            User user = userRepository.findById(request.getUserId()).orElseThrow(
                    () -> new IllegalArgumentException("해당 유저가 없습니다."));
            Store store = Store.builder()
                    .name(request.getStoreName())
                    .address(request.getStoreAddress())
                    .phoneNumber(request.getStorePn())
                    .description(request.getStoreDesc())
                    .maxCapacity(request.getStoreCA())
                    .manager(user)
                    .build();

            storeRepository.save(store);
        } catch (Exception e) {
            return new StoreRegisterResponse(ResponseType.STORE_REGISTER_FAIL);
        }
        return new StoreRegisterResponse(ResponseType.STORE_REGISTER_SUCCESS);
    }

    @Transactional
    @Override
    public StoreUpdateResponse updateStore(StoreUpdateRequest request) {

        try {
            Long storeId = request.getStoreId();

            Store store = storeRepository.findById(storeId).orElseThrow(
                    () -> new IllegalArgumentException("해당 가게가 없습니다.")
            );

            store.update(request);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new StoreUpdateResponse(ResponseType.STORE_EDIT_FAIL);
        }

        return new StoreUpdateResponse(ResponseType.STORE_EDIT_SUCCESS);
    }

    @Transactional
    @Override
    public StoreRemoveResponse removeStore(StoreRemoveRequest request) {

        try {
            Long storeId = request.getStoreId();
            storeRepository.deleteByIdAndDeletedAtIsNull(storeId);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new StoreRemoveResponse(ResponseType.STORE_DELETE_FAIL);
        }
        return new StoreRemoveResponse(ResponseType.STORE_DELETE_SUCCESS);
    }
}
