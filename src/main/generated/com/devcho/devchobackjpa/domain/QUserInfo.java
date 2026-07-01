package com.devcho.devchobackjpa.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserInfo is a Querydsl query type for UserInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserInfo extends EntityPathBase<UserInfo> {

    private static final long serialVersionUID = -628586168L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserInfo userInfo = new QUserInfo("userInfo");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final StringPath password = createString("password");

    public final NumberPath<Integer> phone = createNumber("phone", Integer.class);

    public final StringPath position = createString("position");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final QUserInfo updater;

    public final StringPath userId = createString("userId");

    public final StringPath userName = createString("userName");

    public QUserInfo(String variable) {
        this(UserInfo.class, forVariable(variable), INITS);
    }

    public QUserInfo(Path<? extends UserInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserInfo(PathMetadata metadata, PathInits inits) {
        this(UserInfo.class, metadata, inits);
    }

    public QUserInfo(Class<? extends UserInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.updater = inits.isInitialized("updater") ? new QUserInfo(forProperty("updater"), inits.get("updater")) : null;
    }

}

