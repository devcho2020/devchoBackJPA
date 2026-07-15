package com.devcho.devchobackjpa.dto.code;

import java.util.List;

public record CodeInfoListResponseDTO (
        Long total,
        List<CodeInfoResponseDTO> codeInfoList
) {}
