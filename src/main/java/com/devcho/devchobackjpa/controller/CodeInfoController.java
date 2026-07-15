package com.devcho.devchobackjpa.controller;

import com.devcho.devchobackjpa.dto.code.CodeInfoListResponseDTO;
import com.devcho.devchobackjpa.dto.code.CodeInfoRequestDTO;
import com.devcho.devchobackjpa.dto.code.CodeInfoResponseDTO;
import com.devcho.devchobackjpa.service.CodeInfoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/code-info")
@RequiredArgsConstructor
public class CodeInfoController {

    private final CodeInfoService codeInfoService;

    @GetMapping
    public ResponseEntity<CodeInfoListResponseDTO> getCodeInfoList(
            @RequestParam(defaultValue = "all") String selectedOption,
            @RequestParam(defaultValue = "") String searchValue,
            @RequestParam(required = false) boolean useYn
    ) {
        CodeInfoListResponseDTO response = codeInfoService.getCodeInfoList(selectedOption, searchValue, useYn);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<CodeInfoResponseDTO> getCodeInfoDetail(
            @PathVariable String code) {
        CodeInfoResponseDTO response = codeInfoService.findCodeInfoByCode(code);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> saveCodeInfo(
            @RequestBody CodeInfoRequestDTO dto,
            HttpServletRequest request
    ) {
        Long sessionId = (Long) request.getAttribute("sessionId");
        codeInfoService.saveCodeInfo(dto, sessionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Void> updateCodeInfo(
            @RequestBody CodeInfoRequestDTO dto,
            HttpServletRequest request
    ) {
        Long sessionId = (Long) request.getAttribute("sessionId");
        codeInfoService.updateCodeInfo(dto, sessionId);
        return ResponseEntity.ok().build();
    }
}
