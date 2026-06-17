package com.devcho.devchobackjpa.controller;

import com.devcho.devchobackjpa.dto.freeboard.FreeBoardRequestDTO;
import com.devcho.devchobackjpa.dto.freeboard.FreeBoardResponseDTO;
import com.devcho.devchobackjpa.dto.page.PageResponse;
import com.devcho.devchobackjpa.service.FreeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/free-board")
@RequiredArgsConstructor
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    @GetMapping
    public ResponseEntity<PageResponse<FreeBoardResponseDTO>> getFreeBoards(
            @RequestParam(defaultValue = "all") String selectedOption,
            @RequestParam(defaultValue = "") String searchValue,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<FreeBoardResponseDTO> response = freeBoardService.getFreeBoardList(page, size, selectedOption, searchValue);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{freeBoardId}")
    public ResponseEntity<FreeBoardResponseDTO> getFreeBoardDetail(
            @PathVariable Long freeBoardId
    ) {
        FreeBoardResponseDTO response = freeBoardService.findFreeBoardById(freeBoardId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Long> saveFreeBoard(
            @RequestBody FreeBoardRequestDTO freeBoardRequestDTO
    ) {
        Long saveId = freeBoardService.saveFreeBoard(freeBoardRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveId);
    }

    @PutMapping("/{freeBoardId}")
    public ResponseEntity<Void> updateFreeBoard(
            @PathVariable Long freeBoardId,
            @RequestBody FreeBoardRequestDTO freeBoardRequestDTO
    ) {
        freeBoardService.updateFreeBoard(freeBoardId, freeBoardRequestDTO);
        return ResponseEntity.ok().build();
    }
}
