package com.devcho.devchobackjpa.repository.code;

import com.devcho.devchobackjpa.domain.CodeInfo;
import com.devcho.devchobackjpa.domain.QCodeInfo;
import com.devcho.devchobackjpa.domain.QUserInfo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CodeInfoRepositoryImpl implements CodeInfoRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CodeInfo> searchCodeInfo(String selectedOption, boolean useYn) {
        QCodeInfo codeInfo = QCodeInfo.codeInfo;
        QUserInfo creatorUserInfo = new QUserInfo("creatorUser");
        QUserInfo updaterUserInfo = new QUserInfo("updaterUser");

        return queryFactory
                .selectFrom(codeInfo)
                .leftJoin(codeInfo.creator, creatorUserInfo).fetchJoin()
                .leftJoin(codeInfo.updater, updaterUserInfo).fetchJoin()
                .where(
                        codeInfoSearchOption(selectedOption, useYn)
                )
                .orderBy(codeInfo.codeLevel.asc(), codeInfo.codeSort.asc(), codeInfo.code.asc())   // orderBy 코드 트리를 만들기 위해 항상 고정
                .fetch();
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

    private BooleanExpression codeInfoSearchOption(String selectedOption, boolean useYn) {
        if (selectedOption == null || selectedOption.isBlank()) {
            return  null;
        }

        QCodeInfo codeInfo = QCodeInfo.codeInfo;

//        return switch (selectedOption) {
//            case "useYn" -> codeInfo.useYn.eq(useYn);
//            default -> null;
//        };

        if (selectedOption.equals("useYn")) {
            return codeInfo.useYn.eq(useYn);
        } else {
            return null;
        }
    }
}
