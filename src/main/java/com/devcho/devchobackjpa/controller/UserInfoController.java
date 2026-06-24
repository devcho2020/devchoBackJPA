package com.devcho.devchobackjpa.controller;

import com.devcho.devchobackjpa.dto.page.PageResponse;
import com.devcho.devchobackjpa.dto.user.UserInfoRequestDTO;
import com.devcho.devchobackjpa.dto.user.UserInfoResponseDTO;
import com.devcho.devchobackjpa.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user-info")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping
    public ResponseEntity<PageResponse<UserInfoResponseDTO>> searchUserInfo(
            @RequestParam(required = false) Integer level,
            @RequestParam(defaultValue = "") String searchValue,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<UserInfoResponseDTO> response = userInfoService.searchUserInfo(page, size, level, searchValue);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userInfoId}")
    public ResponseEntity<UserInfoResponseDTO> getUserInfoDetail(
            @PathVariable Long userInfoId) {
        UserInfoResponseDTO response = userInfoService.findUserInfoById(userInfoId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Long> saveUserInfo(
            @RequestBody UserInfoRequestDTO userInfoRequestDTO
    ) {
        Long userInfoId = userInfoService.saveUserInfo(userInfoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userInfoId);
    }

    @PutMapping("/{userInfoId}")
    public ResponseEntity<Void> updateUserInfo(
            @PathVariable Long userInfoId,
            @RequestBody UserInfoRequestDTO userInfoRequestDTO
    ) {
        userInfoService.updateUserInfo(userInfoId, userInfoRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-id")
    public ResponseEntity<Map<String, Object>> checkUserIdDuplication(
            @RequestParam(name = "userId", required = true) String userId
    ) {
        Map<String, Object> response = userInfoService.checkUserIdDuplication(userId);
        return ResponseEntity.ok(response);
    }
}
