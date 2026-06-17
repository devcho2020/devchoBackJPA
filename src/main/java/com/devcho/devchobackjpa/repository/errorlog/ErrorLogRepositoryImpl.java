package com.devcho.devchobackjpa.repository.errorlog;

import com.devcho.devchobackjpa.domain.ErrorLog;
import com.devcho.devchobackjpa.domain.QErrorLog;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ErrorLogRepositoryImpl implements ErrorLogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ErrorLog> searchErrorLogs(String selectedOption, String searchValue, Pageable pageable) {
        QErrorLog errorLog = QErrorLog.errorLog;

        List<ErrorLog> errorLogList = queryFactory
                .selectFrom(errorLog)
                .where(
                    errorLogSearchOption(searchValue, selectedOption)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(errorLog.id.desc())
                .fetch();


        long total = Optional.ofNullable(queryFactory
                .select(errorLog.count())
                .from(errorLog)
                .where(
                    errorLogSearchOption(searchValue, selectedOption)
                )
                .fetchOne()).orElse(0L);


        return new PageImpl<>(errorLogList, pageable, total);
    }

    private BooleanExpression errorLogSearchOption(String searchValue, String selectedOption) {
        if (searchValue == null || searchValue.isBlank()) {
            return null;
        }

        QErrorLog errorLog = QErrorLog.errorLog;

        return switch (selectedOption) {
            case "title" -> errorLog.title.contains(searchValue);
            case "content" -> errorLog.content.contains(searchValue);
            default -> errorLog.title.contains(searchValue).or(errorLog.content.contains(searchValue));
        };
    }
}
