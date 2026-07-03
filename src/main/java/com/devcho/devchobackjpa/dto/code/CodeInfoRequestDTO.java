package com.devcho.devchobackjpa.dto.code;

import com.devcho.devchobackjpa.domain.CodeInfo;
import com.devcho.devchobackjpa.domain.UserInfo;

import java.time.LocalDateTime;

public record CodeInfoRequestDTO(
        String code,
        Integer codeLevel,
        String parentCode,
        String codeName,
        String codeDesc,
        boolean useYn,
        Integer codeSort
) {}
