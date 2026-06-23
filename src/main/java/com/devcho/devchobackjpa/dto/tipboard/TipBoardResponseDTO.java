package com.devcho.devchobackjpa.dto.tipboard;

import com.devcho.devchobackjpa.domain.TipBoard;
import com.devcho.devchobackjpa.domain.UserInfo;

import java.time.LocalDateTime;

public record TipBoardResponseDTO(
        Long id,
        String title,
        String content,
        UserInfo creator,
        LocalDateTime createdAt,
        UserInfo updater,
        LocalDateTime updatedAt
) {
    public static TipBoardResponseDTO from(TipBoard tipBoard) {
        return new TipBoardResponseDTO(
          tipBoard.getId(),
          tipBoard.getTitle(),
          tipBoard.getContent(),
          tipBoard.getCreator(),
          tipBoard.getCreatedAt(),
          tipBoard.getUpdater(),
          tipBoard.getUpdatedAt()
        );
    }
}
