package com.devcho.devchobackjpa.dto.auth;

import com.devcho.devchobackjpa.domain.UserInfo;

public record AuthResponseDTO(
        String token,
        UserInfo userInfo,
        boolean loginResult,
        String failMsg
) {
    public static AuthResponseDTO from(String token, UserInfo userInfo, boolean loginResult, String failMsg) {
        return new AuthResponseDTO(
                token,
                userInfo,
                loginResult,
                failMsg
        );
    }
}
