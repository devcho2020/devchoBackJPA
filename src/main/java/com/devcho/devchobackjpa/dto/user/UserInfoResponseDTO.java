package com.devcho.devchobackjpa.dto.user;

import com.devcho.devchobackjpa.domain.UserInfo;

import java.time.LocalDateTime;

public record UserInfoResponseDTO(
        Long id,
        String userId,
        String userName,
        Integer level,
        Integer phone,
        String position,
        LocalDateTime createdAt
) {
    public static UserInfoResponseDTO from(UserInfo userInfo) {
        return new UserInfoResponseDTO(
                userInfo.getId(),
                userInfo.getUserId(),
                userInfo.getUserName(),
                userInfo.getLevel(),
                userInfo.getPhone(),
                userInfo.getPosition(),
                userInfo.getCreatedAt()
        );
    }
}
