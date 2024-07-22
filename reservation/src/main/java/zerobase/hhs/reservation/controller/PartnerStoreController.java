package zerobase.hhs.reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.hhs.reservation.dto.request.store.StoreRegisterRequest;
import zerobase.hhs.reservation.dto.request.store.StoreRemoveRequest;
import zerobase.hhs.reservation.dto.request.store.StoreUpdateRequest;
import zerobase.hhs.reservation.dto.response.store.StoreRegisterResponse;
import zerobase.hhs.reservation.dto.response.store.StoreRemoveResponse;
import zerobase.hhs.reservation.dto.response.store.StoreUpdateResponse;
import zerobase.hhs.reservation.service.StoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/partner/store")
public class PartnerStoreController {

    private final StoreService storeService;

    @PostMapping("/register")
    public ResponseEntity<StoreRegisterResponse> registerStore(@RequestBody @Valid StoreRegisterRequest request){
        return ResponseEntity.ok(storeService.addStore(request));
    }

    @PatchMapping("/update")
    public ResponseEntity<StoreUpdateResponse> updateStore(@RequestBody @Valid StoreUpdateRequest request){
        return ResponseEntity.ok(storeService.updateStore(request));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<StoreRemoveResponse> removeStore(@RequestBody @Valid StoreRemoveRequest request){
        return ResponseEntity.ok(storeService.removeStore(request));
    }
}
