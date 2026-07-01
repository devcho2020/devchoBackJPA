package com.devcho.devchobackjpa.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTipBoard is a Querydsl query type for TipBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTipBoard extends EntityPathBase<TipBoard> {

    private static final long serialVersionUID = 1910180154L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTipBoard tipBoard = new QTipBoard("tipBoard");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final QUserInfo creator;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final QUserInfo updater;

    public QTipBoard(String variable) {
        this(TipBoard.class, forVariable(variable), INITS);
    }

    public QTipBoard(Path<? extends TipBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTipBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTipBoard(PathMetadata metadata, PathInits inits) {
        this(TipBoard.class, metadata, inits);
    }

    public QTipBoard(Class<? extends TipBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.creator = inits.isInitialized("creator") ? new QUserInfo(forProperty("creator"), inits.get("creator")) : null;
        this.updater = inits.isInitialized("updater") ? new QUserInfo(forProperty("updater"), inits.get("updater")) : null;
    }

}

