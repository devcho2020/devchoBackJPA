package com.devcho.devchobackjpa.repository.freeboard;

import com.devcho.devchobackjpa.domain.FreeBoard;
import com.devcho.devchobackjpa.domain.QFreeBoard;
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
public class FreeBoardRepositoryImpl implements FreeBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FreeBoard> searchFreeBoard(String searchValue, String selectedOption, Pageable pageable) {
        QFreeBoard freeBoard = QFreeBoard.freeBoard;
        QUserInfo creatorUserInfo = new QUserInfo("creatorUser");
        QUserInfo updaterUserInfo = new QUserInfo("updaterUser");

        List<FreeBoard> freeBoardList = queryFactory
                .selectFrom(freeBoard)
                .leftJoin(freeBoard.creator, creatorUserInfo).fetchJoin()
                .leftJoin(freeBoard.updater, updaterUserInfo).fetchJoin()
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

    @Override
    public FreeBoard findFreeBoardByIdWithCreatorAndUpdater(Long id) {
        QFreeBoard freeBoard = QFreeBoard.freeBoard;
        QUserInfo creatorUserInfo = new QUserInfo("creatorUser");
        QUserInfo updaterUserInfo = new QUserInfo("updaterUser");

        FreeBoard result = queryFactory
                .selectFrom(freeBoard)
                .leftJoin(freeBoard.creator, creatorUserInfo).fetchJoin()
                .leftJoin(freeBoard.updater, updaterUserInfo).fetchJoin()
                .where(freeBoard.id.eq(id))
                .fetchOne();
        if (result == null) {
            throw new RuntimeException("존재하지 않는 자유게시글 입니다");
        }
        return result;
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
