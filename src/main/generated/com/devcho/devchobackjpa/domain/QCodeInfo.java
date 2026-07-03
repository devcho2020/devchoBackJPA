package com.devcho.devchobackjpa.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCodeInfo is a Querydsl query type for CodeInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCodeInfo extends EntityPathBase<CodeInfo> {

    private static final long serialVersionUID = -1230083478L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCodeInfo codeInfo = new QCodeInfo("codeInfo");

    public final StringPath code = createString("code");

    public final StringPath codeDesc = createString("codeDesc");

    public final NumberPath<Integer> codeLevel = createNumber("codeLevel", Integer.class);

    public final StringPath codeName = createString("codeName");

    public final NumberPath<Integer> codeSort = createNumber("codeSort", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final QUserInfo creator;

    public final QCodeInfo parentCode;

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final QUserInfo updater;

    public final BooleanPath useYn = createBoolean("useYn");

    public QCodeInfo(String variable) {
        this(CodeInfo.class, forVariable(variable), INITS);
    }

    public QCodeInfo(Path<? extends CodeInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCodeInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCodeInfo(PathMetadata metadata, PathInits inits) {
        this(CodeInfo.class, metadata, inits);
    }

    public QCodeInfo(Class<? extends CodeInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.creator = inits.isInitialized("creator") ? new QUserInfo(forProperty("creator"), inits.get("creator")) : null;
        this.parentCode = inits.isInitialized("parentCode") ? new QCodeInfo(forProperty("parentCode"), inits.get("parentCode")) : null;
        this.updater = inits.isInitialized("updater") ? new QUserInfo(forProperty("updater"), inits.get("updater")) : null;
    }

}

