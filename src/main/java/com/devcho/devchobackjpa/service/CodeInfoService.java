package com.devcho.devchobackjpa.service;

import com.devcho.devchobackjpa.domain.CodeInfo;
import com.devcho.devchobackjpa.domain.UserInfo;
import com.devcho.devchobackjpa.dto.code.CodeInfoListResponseDTO;
import com.devcho.devchobackjpa.dto.code.CodeInfoRequestDTO;
import com.devcho.devchobackjpa.dto.code.CodeInfoResponseDTO;
import com.devcho.devchobackjpa.repository.code.CodeInfoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodeInfoService {

    @PersistenceContext
    private EntityManager em;
    private final CodeInfoRepository codeInfoRepository;

    public CodeInfoListResponseDTO getCodeInfoList(String selectedOption, String searchValue, boolean useYn) {

        List<CodeInfo> content = codeInfoRepository.searchCodeInfo(selectedOption, useYn);
        Long total = (long) content.size();

        Map<String, CodeInfoResponseDTO> dtoMap = content.stream()
                .collect(Collectors.toMap(
                        CodeInfo::getCode,
                        CodeInfoResponseDTO::from // 🎯 작성하신 static 팩토리 메서드 활용!
                ));

        List<CodeInfoResponseDTO> result = new ArrayList<>();

        // codeInfo 부모.children = 자식 작업
        // TODO - 코드 sort 안맞음
        for (CodeInfoResponseDTO dto : dtoMap.values()) {
            if (dto.parentCode() == null || dto.codeLevel() == 0) {
                result.add(dto);
            } else {
                String parentCodeId = dto.parentCode().getCode();
                CodeInfoResponseDTO parentDto = dtoMap.get(parentCodeId);

                if (parentDto != null) {
                    parentDto.children().add(dto);
                }
            }
        }

        if (!selectedOption.isBlank() && !searchValue.isBlank()) {

            String upperCaseSearchValue = searchValue.toUpperCase();

            List<CodeInfoResponseDTO> searchResult = new ArrayList<>();

            for (CodeInfoResponseDTO dto : result) {
                if (isMatched(dto, upperCaseSearchValue, selectedOption)
                    || isSearchChildrenList(dto, upperCaseSearchValue, selectedOption)) {
                    searchResult.add(dto);
                }
            }

            return new CodeInfoListResponseDTO(searchCodeListTotal(searchResult), searchResult);
        } else {

            return new CodeInfoListResponseDTO(total, result);
        }

    }

    private boolean isSearchChildrenList (CodeInfoResponseDTO dto, String searchValue, String selectedOption) {
        boolean result = false;

        for (CodeInfoResponseDTO childDto : dto.children()) {
            if (isMatched(childDto, searchValue, selectedOption)) {
                result = true;
                break;
            } else if (childDto.children() != null && !childDto.children().isEmpty()) {
                result = isSearchChildrenList(childDto, searchValue, selectedOption);
            }
        }

        return result;
    }

    private long searchCodeListTotal(List<CodeInfoResponseDTO> searchResult) {
        long total = 0;
        for (CodeInfoResponseDTO dto : searchResult) {
            total++;
            if (dto.children() != null && !dto.children().isEmpty()) {
                total += searchCodeListTotal(dto.children());
            }
        }
        return total;
    }

    private boolean isMatched(CodeInfoResponseDTO dto, String searchValue, String selectedOption) {
        return switch (selectedOption) {
            case "all" -> dto.code().contains(searchValue) || dto.codeName().contains(searchValue);
            case "code" -> dto.code().contains(searchValue);
            case "codeName" -> dto.codeName().contains(searchValue);
            default -> true;
        };
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

    @Transactional
    public void updateCodeInfo(CodeInfoRequestDTO dto, Long sessionId) {
        CodeInfo codeInfo = codeInfoRepository.findByCode(dto.code())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 코드입니다"));

        UserInfo proxyUser = em.getReference(UserInfo.class, sessionId);

        CodeInfo parentCodeInfo = null;

        if (dto.parentCode() != null) {
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
