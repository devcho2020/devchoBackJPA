package com.devcho.devchobackjpa.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QErrorLog is a Querydsl query type for ErrorLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QErrorLog extends EntityPathBase<ErrorLog> {

    private static final long serialVersionUID = 1034313291L;

    public static final QErrorLog errorLog = new QErrorLog("errorLog");

    public final StringPath area = createString("area");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath title = createString("title");

    public QErrorLog(String variable) {
        super(ErrorLog.class, forVariable(variable));
    }

    public QErrorLog(Path<? extends ErrorLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QErrorLog(PathMetadata metadata) {
        super(ErrorLog.class, metadata);
    }

}

