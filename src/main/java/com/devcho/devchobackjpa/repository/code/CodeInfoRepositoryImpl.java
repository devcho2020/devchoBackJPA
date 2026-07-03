package com.devcho.devchobackjpa.repository.code;

import com.devcho.devchobackjpa.domain.CodeInfo;
import com.devcho.devchobackjpa.domain.QCodeInfo;
import com.devcho.devchobackjpa.domain.QUserInfo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CodeInfoRepositoryImpl implements CodeInfoRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CodeInfo> searchCodeInfo(String searchValue, String selectedOption, Pageable pageable) {
        QCodeInfo codeInfo = QCodeInfo.codeInfo;
        QUserInfo creatorUserInfo = new QUserInfo("creatorUser");
        QUserInfo updaterUserInfo = new QUserInfo("updaterUser");

        List<CodeInfo> codeInfoList = queryFactory
                .selectFrom(codeInfo)
                .leftJoin(codeInfo.creator, creatorUserInfo).fetchJoin()
                .leftJoin(codeInfo.updater, updaterUserInfo).fetchJoin()
                .where(
                        codeInfoSearchOption(searchValue, selectedOption)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(codeInfo.codeSort.desc(), codeInfo.code.asc())
                .fetch();

        Long total = Optional.ofNullable(queryFactory
                .select(codeInfo.count())
                .where(
                        codeInfoSearchOption(searchValue, selectedOption)
                )
                .fetchOne()).orElse(0L);
        return new PageImpl<>(codeInfoList, pageable, total);
    }

    @Override
    public CodeInfo findByCodeWithCreatorAndUpdater(String code) {
        QCodeInfo codeInfo = QCodeInfo.codeInfo;
        QUserInfo creatorUserInfo = new QUserInfo("creatorUser");
        QUserInfo updaterUserInfo = new QUserInfo("updaterUser");

        CodeInfo result = queryFactory
                .selectFrom(codeInfo)
                .leftJoin(codeInfo.creator, creatorUserInfo).fetchJoin()
                .leftJoin(codeInfo.updater, updaterUserInfo).fetchJoin()
                .where(codeInfo.code.eq(code))
                .fetchOne();

        if (result == null) {
            throw new RuntimeException("존재하지 않는 코드 정보 입니다");
        }
        return result;
    }

    private BooleanExpression codeInfoSearchOption(String searchValue, String selectedOption) {
        if (selectedOption == null || selectedOption.isBlank()) {
            return  null;
        }

        QCodeInfo codeInfo = QCodeInfo.codeInfo;

        return switch (selectedOption) {
            case "code" -> codeInfo.code.contains(searchValue);
            case "codeName" -> codeInfo.codeName.contains(searchValue);
            case "parentCode" -> codeInfo.parentCode.code.eq(searchValue);
            default -> codeInfo.code.contains(searchValue).and(codeInfo.codeName.contains(searchValue));
        };
    }
}
