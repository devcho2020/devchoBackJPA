package com.devcho.devchobackjpa.controller;

import com.devcho.devchobackjpa.dto.page.PageResponse;
import com.devcho.devchobackjpa.dto.tipboard.TipBoardRequestDTO;
import com.devcho.devchobackjpa.dto.tipboard.TipBoardResponseDTO;
import com.devcho.devchobackjpa.service.TipBoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tip-board")
@RequiredArgsConstructor
public class TipBoardController {

    private final TipBoardService tipBoardService;

    @GetMapping
    public ResponseEntity<PageResponse<TipBoardResponseDTO>> getTipBoardList(
            @RequestParam(defaultValue = "all") String selectedOption,
            @RequestParam(defaultValue = "") String searchValue,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<TipBoardResponseDTO> response = tipBoardService.getTipBoardList(page, size, selectedOption, searchValue);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{tipBoardId}")
    public ResponseEntity<TipBoardResponseDTO> getTipBoardById(@PathVariable Long tipBoardId) {
        TipBoardResponseDTO response = tipBoardService.findTipBoardById(tipBoardId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Long> saveTipBoard(
            HttpServletRequest request,
            @RequestBody TipBoardRequestDTO tipBoardRequestDTO
    ) {
        Long sessionId = (Long) request.getAttribute("sessionId");
        Long saveId = tipBoardService.saveTipBoard(tipBoardRequestDTO, sessionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveId);
    }

    @PutMapping("/{tipBoardId}")
    public ResponseEntity<Void> updateTipBoard(
            HttpServletRequest request,
            @PathVariable Long tipBoardId,
            @RequestBody TipBoardRequestDTO tipBoardRequestDTO
    ) {
        Long sessionId = (Long) request.getAttribute("sessionId");
        tipBoardService.updateTipBoard(tipBoardId, tipBoardRequestDTO, sessionId);
        return ResponseEntity.ok().build();
    }
}
