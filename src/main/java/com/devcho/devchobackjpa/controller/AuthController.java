package com.devcho.devchobackjpa.controller;

import com.devcho.devchobackjpa.dto.auth.AuthResponseDTO;
import com.devcho.devchobackjpa.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody Map<String, String> loginInfo
    ) {
        String loginId = loginInfo.get("loginId");
        String loginPassword = loginInfo.get("loginPassword");
        AuthResponseDTO response = authService.login(loginId, loginPassword);
        return ResponseEntity.ok(response);
    }
}
