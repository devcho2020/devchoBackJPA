package com.devcho.devchobackjpa.controller;

import com.devcho.devchobackjpa.dto.errorlog.ErrorLogRequestDTO;
import com.devcho.devchobackjpa.dto.errorlog.ErrorLogResponseDTO;
import com.devcho.devchobackjpa.dto.page.PageResponse;
import com.devcho.devchobackjpa.service.ErrorLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/error-log")
@RequiredArgsConstructor
public class ErrorLogController {

    private final ErrorLogService errorLogService;

    @GetMapping
    public ResponseEntity<PageResponse<ErrorLogResponseDTO>> getErrorLogs(
            @RequestParam(defaultValue = "all") String selectedOption,
            @RequestParam(defaultValue = "") String searchValue,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<ErrorLogResponseDTO> response = errorLogService.getErrorLogList(page, size, selectedOption, searchValue);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{errorLogId}")
    public ResponseEntity<ErrorLogResponseDTO> getErrorLogDetail(
            @PathVariable Long errorLogId
    ) {
        ErrorLogResponseDTO response = errorLogService.findErrorLogById(errorLogId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Long> saveErrorLog(
            HttpServletRequest request,
            @RequestBody ErrorLogRequestDTO dto
    ) {
        Long sessionId = (Long) request.getAttribute("sessionId");
        Long saveId = errorLogService.saveErrorLog(dto, sessionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveId);
    }

    @PutMapping("/{errorLogId}")
    public ResponseEntity<Void> updateErrorLog(
            HttpServletRequest request,
            @PathVariable Long errorLogId,
            @RequestBody ErrorLogRequestDTO dto) {
        Long sessionId = (Long) request.getAttribute("sessionId");
        errorLogService.updateErrorLog(errorLogId, dto, sessionId);
        return ResponseEntity.ok().build();
    }
}
