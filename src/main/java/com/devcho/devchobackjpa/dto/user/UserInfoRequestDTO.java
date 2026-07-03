package com.devcho.devchobackjpa.dto.user;

import com.devcho.devchobackjpa.domain.UserInfo;

public record UserInfoRequestDTO(
        String userId,
        String userName,
        String password,
        Integer level,
        Integer phone,
        String position
) {}
