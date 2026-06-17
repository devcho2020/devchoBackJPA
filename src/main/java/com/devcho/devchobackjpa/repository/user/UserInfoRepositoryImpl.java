package com.devcho.devchobackjpa.repository.user;

import com.devcho.devchobackjpa.domain.QUserInfo;
import com.devcho.devchobackjpa.domain.UserInfo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserInfoRepositoryImpl implements UserInfoRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<UserInfo> searchUserInfo(String searchValue, Integer level, Pageable pageable) {

        QUserInfo userInfo = QUserInfo.userInfo;

        List<UserInfo> userInfoList = queryFactory
                .selectFrom(userInfo)
                .where(
                        userInfoSearchOption(searchValue, level)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(userInfo.id.desc())
                .fetch();

        Long total = Optional.ofNullable(queryFactory
                .select(userInfo.count())
                .from(userInfo)
                .where(
                        userInfoSearchOption(searchValue, level)
                )
                .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(userInfoList, pageable, total);
    }

    private BooleanExpression userInfoSearchOption(String searchValue, Integer level) {
        QUserInfo userInfo = QUserInfo.userInfo;

        if (level != null) {
            return userInfo.level.eq(level).and(userInfo.userName.contains(searchValue));
        } else {
            return userInfo.userName.contains(searchValue);
        }
    }
}
