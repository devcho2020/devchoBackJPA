package com.devcho.devchobackjpa.dto.tipboard;

import com.devcho.devchobackjpa.domain.TipBoard;

public record TipBoardRequestDTO(
        Long id,
        String title,
        String content
) {
    public TipBoard toEntity() {
        return TipBoard.builder()
                .title(title)
                .content(content)
                .build();
    }
}
