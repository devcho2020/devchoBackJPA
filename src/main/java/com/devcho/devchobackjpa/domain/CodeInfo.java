package com.devcho.devchobackjpa.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.type.YesNoConverter;

import java.time.LocalDateTime;

@Entity
@Table(name = "code_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeInfo {

    @Id
    private String code;

    // 0부터 최상위 코드
    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer codeLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_code")
    private CodeInfo parentCode;

    private String codeName;

    @Column(columnDefinition = "TEXT")
    private String codeDesc;

    @Column(columnDefinition = "VARCHAR(1) DEFAULT 'Y'")
    @Convert(converter = YesNoConverter.class)  // Y - true, N - false 자동 변환
    private boolean useYn = true;

    @Column(columnDefinition = "INT DEFAULT 999")
    private Integer codeSort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    private UserInfo creator;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updater")
    private UserInfo updater;
    private LocalDateTime updatedAt;

    @Builder
    public CodeInfo(String code, Integer codeLevel, CodeInfo parentCode, String codeName, String codeDesc, boolean useYn, Integer codeSort, UserInfo creator, LocalDateTime createdAt) {
        this.code = code;
        this.codeLevel = codeLevel;
        this.parentCode = parentCode;
        this.codeName = codeName;
        this.codeDesc = codeDesc;
        this.useYn = useYn;
        this.codeSort = codeSort;
        this.creator = creator;
        this.createdAt = LocalDateTime.now();
        this.updater = creator;
    }

    public void update(Integer codeLevel, CodeInfo parentCode, String codeName, String codeDesc, boolean useYn, Integer codeSort, UserInfo updater) {
        this.codeLevel = codeLevel;
        this.parentCode = parentCode;
        this.codeName = codeName;
        this.codeDesc = codeDesc;
        this.useYn = useYn;
        this.codeSort = codeSort;
        this.updater = updater;
        this.updatedAt = LocalDateTime.now();
    }
}
