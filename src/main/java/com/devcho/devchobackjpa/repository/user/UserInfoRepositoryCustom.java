package com.devcho.devchobackjpa.repository.user;

import com.devcho.devchobackjpa.domain.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserInfoRepositoryCustom {
    Page<UserInfo> searchUserInfo(String searchValue, Integer level, Pageable pageable);
}
