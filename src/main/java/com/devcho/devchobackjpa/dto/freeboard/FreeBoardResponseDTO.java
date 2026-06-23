package com.devcho.devchobackjpa.dto.freeboard;

import com.devcho.devchobackjpa.domain.FreeBoard;
import com.devcho.devchobackjpa.domain.UserInfo;

import java.time.LocalDateTime;

public record FreeBoardResponseDTO(
        Long id,
        String title,
        String content,
        UserInfo creator,
        LocalDateTime createdAt,
        UserInfo updater,
        LocalDateTime updatedAt
) {
    public static FreeBoardResponseDTO from(FreeBoard freeBoard) {
        return new FreeBoardResponseDTO(
                freeBoard.getId(),
                freeBoard.getTitle(),
                freeBoard.getContent(),
                freeBoard.getCreator(),
                freeBoard.getCreatedAt(),
                freeBoard.getUpdater(),
                freeBoard.getUpdatedAt()
        );
    }
}
