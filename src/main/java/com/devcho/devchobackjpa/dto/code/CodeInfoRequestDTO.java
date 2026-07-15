package com.devcho.devchobackjpa.dto.code;

public record CodeInfoRequestDTO(
        String code,
        Integer codeLevel,
        String parentCode,
        String codeName,
        String codeDesc,
        boolean useYn,
        Integer codeSort
) {}
