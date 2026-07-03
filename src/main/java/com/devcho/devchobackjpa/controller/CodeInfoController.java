package com.devcho.devchobackjpa.controller;

import com.devcho.devchobackjpa.dto.code.CodeInfoRequestDTO;
import com.devcho.devchobackjpa.dto.code.CodeInfoResponseDTO;
import com.devcho.devchobackjpa.dto.page.PageResponse;
import com.devcho.devchobackjpa.service.CodeInfoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
public class CodeInfoController {

    private final CodeInfoService codeInfoService;

    @GetMapping
    public ResponseEntity<PageResponse<CodeInfoResponseDTO>> getCodeInfoList(
            @RequestParam(defaultValue = "all") String selectedOption,
            @RequestParam(defaultValue = "") String searchValue,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<CodeInfoResponseDTO> response = codeInfoService.getCodeInfoList(page, size, selectedOption, searchValue);
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
