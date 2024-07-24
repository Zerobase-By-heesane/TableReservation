package zerobase.hhs.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.hhs.reservation.domain.Store;
import zerobase.hhs.reservation.domain.User;
import zerobase.hhs.reservation.dto.request.store.StoreRegisterRequest;
import zerobase.hhs.reservation.dto.request.store.StoreRemoveRequest;
import zerobase.hhs.reservation.dto.request.store.StoreUpdateRequest;
import zerobase.hhs.reservation.dto.response.store.*;
import zerobase.hhs.reservation.exception.ExceptionsType;
import zerobase.hhs.reservation.exception.exceptions.CannotFindStore;
import zerobase.hhs.reservation.exception.exceptions.CannotFindUser;
import zerobase.hhs.reservation.repository.StoreRepository;
import zerobase.hhs.reservation.repository.UserRepository;
import zerobase.hhs.reservation.service.StoreService;
import zerobase.hhs.reservation.type.RedisKeyType;
import zerobase.hhs.reservation.type.ResponseType;

@Slf4j
@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    /**
     * 존재하는 모든 가게의 정보를 조회하는 서비스 구현체
     * @return 모든 가게의 리스트 정보
     * deletedAt이 null인 모든 가게 리스트 반환
     */
    @Override
    @Cacheable(key="'stores'", value= RedisKeyType.STORES)
    public StoreListResponse getStoreList() {
        return new StoreListResponse(StoreResponse.list(storeRepository.findAllByDeletedAtIsNull()));
    }

    /**
     * 가게를 등록하는 서비스 구현체
     *
     * @param request 가게 등록 정보 요청
     *                - userId : 유저 아이디
     *                - storeName : 가게 이름
     *                - storeAddress : 가게 주소
     *                - storePn : 가게 전화번호
     *                - storeDesc : 가게 설명
     *                - storeCA : 가게 수용인원
     * @return 가게 등록 결과 응답
     */
    @Transactional
    @Override
    public StoreRegisterResponse addStore(StoreRegisterRequest request) {

        try {
            User user = userRepository.findById(request.getUserId()).orElseThrow(
                    () -> new CannotFindUser(ExceptionsType.CANNOT_FIND_USER));
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

    /**
     * 가게를 수정하는 서비스 구현체
     *
     * @param request 가게 수정 정보 요청
     *                - storeId : 가게 아이디
     *                - storeName : 가게 이름
     *                - storeAddress : 가게 주소
     *                - storePn : 가게 전화번호
     *                - storeDesc : 가게 설명
     *                - storeCA : 가게 수용인원
     *                - userId : 유저 아이디
     * @return 가게 수정 결과 응답
     */
    @Transactional
    @Override
    public StoreUpdateResponse updateStore(StoreUpdateRequest request) {

        try {
            Long storeId = request.getStoreId();

            Store store = storeRepository.findById(storeId).orElseThrow(
                    () -> new CannotFindStore(ExceptionsType.CANNOT_FIND_STORE)
            );

            store.update(request);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new StoreUpdateResponse(ResponseType.STORE_EDIT_FAIL);
        }

        return new StoreUpdateResponse(ResponseType.STORE_EDIT_SUCCESS);
    }

    /**
     * 가게를 삭제하는 서비스 구현체
     * @param request 가게 삭제 정보 요청
     * @return 가게 삭제 결과 응답
     */
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
