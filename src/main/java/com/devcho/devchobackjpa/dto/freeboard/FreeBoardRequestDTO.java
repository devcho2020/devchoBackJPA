package com.devcho.devchobackjpa.dto.freeboard;

import com.devcho.devchobackjpa.domain.FreeBoard;

public record FreeBoardRequestDTO(
        Long id,
        String title,
        String content
) {
    public FreeBoard toEntity() {
        return FreeBoard.builder()
                .title(title)
                .content(content)
                .build();

    }
}
