package com.devcho.devchobackjpa.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QErrorLog is a Querydsl query type for ErrorLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QErrorLog extends EntityPathBase<ErrorLog> {

    private static final long serialVersionUID = 1034313291L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QErrorLog errorLog = new QErrorLog("errorLog");

    public final StringPath area = createString("area");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final QUserInfo creator;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final QUserInfo updater;

    public QErrorLog(String variable) {
        this(ErrorLog.class, forVariable(variable), INITS);
    }

    public QErrorLog(Path<? extends ErrorLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QErrorLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QErrorLog(PathMetadata metadata, PathInits inits) {
        this(ErrorLog.class, metadata, inits);
    }

    public QErrorLog(Class<? extends ErrorLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.creator = inits.isInitialized("creator") ? new QUserInfo(forProperty("creator")) : null;
        this.updater = inits.isInitialized("updater") ? new QUserInfo(forProperty("updater")) : null;
    }

}

