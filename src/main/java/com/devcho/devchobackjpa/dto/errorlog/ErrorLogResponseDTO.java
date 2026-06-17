package com.devcho.devchobackjpa.dto.errorlog;

import java.time.LocalDateTime;
import com.devcho.devchobackjpa.domain.ErrorLog;

public record ErrorLogResponseDTO(
        Long id,
        String title,
        String area,
        String content,
        LocalDateTime createdAt
) {
    public static ErrorLogResponseDTO
        from(ErrorLog errorLog) {
        return new ErrorLogResponseDTO(
                errorLog.getId(),
                errorLog.getTitle(),
                errorLog.getArea(),
                errorLog.getContent(),
                errorLog.getCreatedAt()
        );
    }
}
