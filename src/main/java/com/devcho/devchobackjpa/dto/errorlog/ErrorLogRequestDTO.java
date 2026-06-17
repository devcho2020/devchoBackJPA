package com.devcho.devchobackjpa.dto.errorlog;

import com.devcho.devchobackjpa.domain.ErrorLog;

public record ErrorLogRequestDTO(
        Long id,
        String title,
        String area,
        String content
) {
    public ErrorLog toEntity() {
        return ErrorLog.builder()
                .title(title)
                .content(content)
                .area(area)
                .build();
    }
}
