package com.devcho.devchobackjpa.service;

import com.devcho.devchobackjpa.domain.ErrorLog;
import com.devcho.devchobackjpa.dto.errorlog.ErrorLogRequestDTO;
import com.devcho.devchobackjpa.dto.errorlog.ErrorLogResponseDTO;
import com.devcho.devchobackjpa.dto.page.PageResponse;
import com.devcho.devchobackjpa.repository.errorlog.ErrorLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ErrorLogService {
    private final ErrorLogRepository errorLogRepository;

    public PageResponse<ErrorLogResponseDTO> getErrorLogList(int page, int size, String selectedOption, String searchValue) {

        Pageable pageable = PageRequest.of(page, size);

        // 이제 repository에서 Querydsl로 짠 검색 메서드를 바로 쓸 수 있습니다.
        Page<ErrorLog> result = errorLogRepository.searchErrorLogs(selectedOption, searchValue, pageable);

        List<ErrorLogResponseDTO> content = result.getContent().stream()
                .map(ErrorLogResponseDTO::from)
                .collect(Collectors.toList());

        return new PageResponse<>(content, result.getTotalPages(), result.getTotalElements());
    }

    public ErrorLogResponseDTO findErrorLogById(Long errorLogId) {
        ErrorLog errorLog = errorLogRepository
                .findById(errorLogId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 에러 로그입니다"));
        return ErrorLogResponseDTO.from(errorLog);
    }

    @Transactional
    public Long saveErrorLog(ErrorLogRequestDTO dto) {
        ErrorLog errorLog = dto.toEntity();
        return errorLogRepository.save(errorLog).getId();
    }

    @Transactional
    public void updateErrorLog(Long errorLogId,ErrorLogRequestDTO dto) {
        ErrorLog errorLog = errorLogRepository
                .findById(errorLogId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 에러 로그입니다"));

        errorLog.update(dto.title(), dto.area(), dto.content());
    }
}
