package com.devcho.devchobackjpa.service;

import com.devcho.devchobackjpa.domain.CodeInfo;
import com.devcho.devchobackjpa.domain.UserInfo;
import com.devcho.devchobackjpa.dto.code.CodeInfoRequestDTO;
import com.devcho.devchobackjpa.dto.code.CodeInfoResponseDTO;
import com.devcho.devchobackjpa.dto.page.PageResponse;
import com.devcho.devchobackjpa.repository.code.CodeInfoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodeInfoService {

    @PersistenceContext
    private EntityManager em;
    private final CodeInfoRepository codeInfoRepository;

    public PageResponse<CodeInfoResponseDTO> getCodeInfoList(int page, int size, String selectedOption, String searchValue) {

        PageRequest pageable = PageRequest.of(page, size);

        Page<CodeInfo> result = codeInfoRepository.searchCodeInfo(searchValue, selectedOption, pageable);

        List<CodeInfoResponseDTO> content = result.getContent().stream()
                .map(CodeInfoResponseDTO::from)
                .collect(Collectors.toList());

        return new PageResponse<>(content, result.getTotalPages(), result.getTotalElements());
    }

    public CodeInfoResponseDTO findCodeInfoByCode(String code) {
        CodeInfo codeinfo = codeInfoRepository.findByCodeWithCreatorAndUpdater(code);
        return CodeInfoResponseDTO.from(codeinfo);
    }

    @Transactional
    public void saveCodeInfo(CodeInfoRequestDTO dto, Long sessionId) {
        UserInfo proxyUser = em.getReference(UserInfo.class, sessionId);

        CodeInfo parentCodeInfo = null;

        if (!dto.parentCode().isBlank()) {
            parentCodeInfo = codeInfoRepository.findByCode(dto.parentCode())
                    .orElseThrow(() -> new RuntimeException("부모 코드가 존재하지 않습니다"));
        }

        CodeInfo codeInfo = CodeInfo.builder()
                .code(dto.code())
                .codeLevel(dto.codeLevel())
                .parentCode(parentCodeInfo)
                .codeName(dto.codeName())
                .codeDesc(dto.codeDesc())
                .useYn(dto.useYn())
                .codeSort(dto.codeSort())
                .creator(proxyUser)
                .build();

        codeInfoRepository.save(codeInfo);
    }

    public void updateCodeInfo(CodeInfoRequestDTO dto, Long sessionId) {
        CodeInfo codeInfo = codeInfoRepository.findByCode(dto.code())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 코드입니다"));

        UserInfo proxyUser = em.getReference(UserInfo.class, sessionId);

        CodeInfo parentCodeInfo = null;

        if (!dto.parentCode().isBlank()) {
            parentCodeInfo = codeInfoRepository.findByCode(dto.parentCode())
                    .orElseThrow(() -> new RuntimeException("부모 코드가 존재하지 않습니다"));
        }

        codeInfo.update(
                dto.codeLevel(),
                parentCodeInfo,
                dto.codeName(),
                dto.codeDesc(),
                dto.useYn(),
                dto.codeSort(),
                proxyUser
        );
    }
}
