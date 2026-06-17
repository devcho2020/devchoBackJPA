package com.devcho.devchobackjpa.dto.freeboard;

import com.devcho.devchobackjpa.domain.FreeBoard;

import java.time.LocalDateTime;

public record FreeBoardResponseDTO(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static FreeBoardResponseDTO from(FreeBoard freeBoard) {
        return new FreeBoardResponseDTO(
                freeBoard.getId(),
                freeBoard.getTitle(),
                freeBoard.getContent(),
                freeBoard.getCreatedAt(),
                freeBoard.getUpdatedAt()
        );
    }
}
