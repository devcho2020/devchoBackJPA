package com.devcho.devchobackjpa.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTipBoard is a Querydsl query type for TipBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTipBoard extends EntityPathBase<TipBoard> {

    private static final long serialVersionUID = 1910180154L;

    public static final QTipBoard tipBoard = new QTipBoard("tipBoard");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QTipBoard(String variable) {
        super(TipBoard.class, forVariable(variable));
    }

    public QTipBoard(Path<? extends TipBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTipBoard(PathMetadata metadata) {
        super(TipBoard.class, metadata);
    }

}

