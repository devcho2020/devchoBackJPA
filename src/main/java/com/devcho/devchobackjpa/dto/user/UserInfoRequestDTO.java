package com.devcho.devchobackjpa.dto.user;

import com.devcho.devchobackjpa.domain.UserInfo;

public record UserInfoRequestDTO(
        String userId,
        String userName,
        String password,
        Integer level,
        Integer phone,
        String position
) {
    public UserInfo toEntity() {
        return UserInfo.builder()
                .userId(userId)
                .userName(userName)
                .password(password)
                .level(level)
                .phone(phone)
                .position(position)
                .build();
    }
}
