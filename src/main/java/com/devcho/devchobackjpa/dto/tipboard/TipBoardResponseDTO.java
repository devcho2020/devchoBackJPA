package com.devcho.devchobackjpa.dto.tipboard;

import com.devcho.devchobackjpa.domain.TipBoard;

import java.time.LocalDateTime;

public record TipBoardResponseDTO(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static TipBoardResponseDTO from(TipBoard tipBoard) {
        return new TipBoardResponseDTO(
          tipBoard.getId(),
          tipBoard.getTitle(),
          tipBoard.getContent(),
          tipBoard.getCreatedAt(),
          tipBoard.getUpdatedAt()
        );
    }
}
