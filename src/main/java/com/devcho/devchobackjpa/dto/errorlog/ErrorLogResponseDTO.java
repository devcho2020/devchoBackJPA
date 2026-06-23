package com.devcho.devchobackjpa.dto.errorlog;

import com.devcho.devchobackjpa.domain.ErrorLog;
import com.devcho.devchobackjpa.domain.UserInfo;

import java.time.LocalDateTime;

public record ErrorLogResponseDTO(
        Long id,
        String title,
        String area,
        String content,
        UserInfo creator,
        UserInfo updater,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ErrorLogResponseDTO
        from(ErrorLog errorLog) {
        return new ErrorLogResponseDTO(
                errorLog.getId(),
                errorLog.getTitle(),
                errorLog.getArea(),
                errorLog.getContent(),
                errorLog.getCreator(),
                errorLog.getUpdater(),
                errorLog.getCreatedAt(),
                errorLog.getUpdatedAt()
        );
    }
}
