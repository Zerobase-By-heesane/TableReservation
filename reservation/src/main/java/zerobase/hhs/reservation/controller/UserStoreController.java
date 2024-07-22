package zerobase.hhs.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.hhs.reservation.dto.response.store.StoreListResponse;
import zerobase.hhs.reservation.service.StoreService;

/**
 * 사용자가 사용하는 컨트롤러
 */
@RestController
@RequestMapping("/api/user/store")
@RequiredArgsConstructor
public class UserStoreController {

    private final StoreService storeService;

    @GetMapping("/list")
    public ResponseEntity<StoreListResponse> getStoreList(){
        return ResponseEntity.ok(storeService.getStoreList());
    }
}
