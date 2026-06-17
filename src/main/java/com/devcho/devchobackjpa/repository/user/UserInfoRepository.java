package com.devcho.devchobackjpa.repository.user;

import com.devcho.devchobackjpa.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>, UserInfoRepositoryCustom {
    boolean existsByUserId(String userId);
}
