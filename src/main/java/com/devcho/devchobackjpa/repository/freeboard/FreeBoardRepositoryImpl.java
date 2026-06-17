package com.devcho.devchobackjpa.repository.freeboard;

import com.devcho.devchobackjpa.domain.FreeBoard;
import com.devcho.devchobackjpa.domain.QFreeBoard;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class FreeBoardRepositoryImpl implements FreeBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FreeBoard> searchFreeBoard(String searchValue, String selectedOption, Pageable pageable) {
        QFreeBoard freeBoard = QFreeBoard.freeBoard;

        List<FreeBoard> freeBoardList = queryFactory
                .selectFrom(freeBoard)
                .where(
                        freeBoardSearchOption(searchValue, selectedOption)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(freeBoard.id.desc())
                .fetch();

        Long total =  Optional.ofNullable(queryFactory
                .select(freeBoard.count())
                .from(freeBoard)
                .where(
                        freeBoardSearchOption(searchValue, selectedOption)
                )
                .fetchOne()).orElse(0L);

        return new PageImpl<>(freeBoardList, pageable, total);
    }

    private BooleanExpression freeBoardSearchOption(String searchValue, String selectedOption) {
        if (selectedOption == null || selectedOption.isBlank()) {
            return  null;
        }

        QFreeBoard freeBoard = QFreeBoard.freeBoard;

        return switch (selectedOption) {
            case "title" -> freeBoard.title.contains(searchValue);
            case "content" -> freeBoard.content.contains(searchValue);
            default -> freeBoard.title.contains(searchValue).or(freeBoard.content.contains(searchValue));
        };
    }
}
