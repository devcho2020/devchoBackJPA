package com.devcho.devchobackjpa.repository.tipboard;

import com.devcho.devchobackjpa.domain.QFreeBoard;
import com.devcho.devchobackjpa.domain.QTipBoard;
import com.devcho.devchobackjpa.domain.QUserInfo;
import com.devcho.devchobackjpa.domain.TipBoard;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TipBoardRepositoryImpl implements TipBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<TipBoard> searchTipBoards(String searchValue, String selectedOption, Pageable pageable) {

        QTipBoard tipBoard = QTipBoard.tipBoard;
        QUserInfo creatorUserInfo = new QUserInfo("creatorUser");
        QUserInfo updaterUserInfo = new QUserInfo("updaterUser");

        List<TipBoard> tipBoardList = queryFactory
                .selectFrom(tipBoard)
                .leftJoin(tipBoard.creator, creatorUserInfo).fetchJoin()
                .leftJoin(tipBoard.updater, updaterUserInfo).fetchJoin()
                .where(
                        tipBoardSearchOption(searchValue, selectedOption)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(tipBoard.id.desc())
                .fetch();

        Long total = Optional.ofNullable(queryFactory
                .select(tipBoard.count())
                .from(tipBoard)
                .where(
                        tipBoardSearchOption(searchValue, selectedOption)
                )
                .fetchOne()).orElse(0L);

        return new PageImpl<>(tipBoardList, pageable, total);
    }

    @Override
    public TipBoard findByIdWithCreatorAndUpdater(Long id) {
        QTipBoard tipBoard = QTipBoard.tipBoard;
        QUserInfo creatorUserInfo = new QUserInfo("creatorUser");
        QUserInfo updaterUserInfo = new QUserInfo("updaterUser");

        TipBoard result = queryFactory
                .selectFrom(tipBoard)
                .leftJoin(tipBoard.creator, creatorUserInfo).fetchJoin()
                .leftJoin(tipBoard.updater, updaterUserInfo).fetchJoin()
                .where(tipBoard.id.eq(id))
                .fetchOne();
        if (result == null) {
            throw new RuntimeException("존재하지 않는 팁 게시글 입니다");
        }
        return result;
    }

    private BooleanExpression tipBoardSearchOption(String searchValue, String selectedOption) {
        if (selectedOption == null || selectedOption.isBlank()) {
            return  null;
        }

        QTipBoard tipBoard = QTipBoard.tipBoard;

        return switch (selectedOption) {
            case "title" -> tipBoard.title.contains(searchValue);
            case "content" -> tipBoard.content.contains(searchValue);
            default -> tipBoard.title.contains(searchValue).or(tipBoard.content.contains(searchValue));
        };
    }
}
