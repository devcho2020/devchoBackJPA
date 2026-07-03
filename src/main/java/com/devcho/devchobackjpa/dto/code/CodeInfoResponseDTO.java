package com.devcho.devchobackjpa.dto.code;

import com.devcho.devchobackjpa.domain.CodeInfo;
import com.devcho.devchobackjpa.domain.UserInfo;

import java.time.LocalDateTime;

public record CodeInfoResponseDTO(
        String code,
        Integer codeLevel,
        CodeInfo parentCode,
        String codeName,
        String codeDesc,
        boolean useYn,
        Integer codeSort,
        UserInfo creator,
        LocalDateTime createdAt,
        UserInfo updater,
        LocalDateTime updatedAt
) {
    public static CodeInfoResponseDTO
        from(CodeInfo codeInfo) {
            return new CodeInfoResponseDTO(
                codeInfo.getCode(),
                codeInfo.getCodeLevel(),
                codeInfo.getParentCode(),
                codeInfo.getCodeName(),
                codeInfo.getCodeDesc(),
                codeInfo.isUseYn(),
                codeInfo.getCodeSort(),
                codeInfo.getCreator(),
                codeInfo.getCreatedAt(),
                codeInfo.getUpdater(),
                codeInfo.getUpdatedAt()
        );
    }
}
