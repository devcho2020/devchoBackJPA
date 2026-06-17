package com.devcho.devchobackjpa.repository.auth;

import com.devcho.devchobackjpa.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findUserInfoByUserId(String userId);
}
