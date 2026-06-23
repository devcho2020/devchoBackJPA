package com.devcho.devchobackjpa.service;

import com.devcho.devchobackjpa.domain.FreeBoard;
import com.devcho.devchobackjpa.domain.UserInfo;
import com.devcho.devchobackjpa.dto.freeboard.FreeBoardRequestDTO;
import com.devcho.devchobackjpa.dto.freeboard.FreeBoardResponseDTO;
import com.devcho.devchobackjpa.dto.page.PageResponse;
import com.devcho.devchobackjpa.repository.freeboard.FreeBoardRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
public class FreeBoardService {

    @PersistenceContext
    private EntityManager em;
    private final FreeBoardRepository freeBoardRepository;

    public PageResponse<FreeBoardResponseDTO> getFreeBoardList(int page, int size, String selectedOption, String searchValue) {

        Pageable pageable = PageRequest.of(page, size);

        Page<FreeBoard> result = freeBoardRepository.searchFreeBoard(searchValue, selectedOption, pageable);

        List<FreeBoardResponseDTO> content = result.getContent().stream()
                .map(FreeBoardResponseDTO::from)
                .collect(Collectors.toList());

        return new PageResponse<>(content, result.getTotalPages(), result.getTotalElements());
    }

    public FreeBoardResponseDTO findFreeBoardById(Long freeBoardId) {
        FreeBoard freeBoard = freeBoardRepository.findFreeBoardByIdWithCreatorAndUpdater(freeBoardId);
        return FreeBoardResponseDTO.from(freeBoard);
    }

    @Transactional
    public Long saveFreeBoard(FreeBoardRequestDTO dto, Long userInfoId  ) {
        UserInfo proxyUser = em.getReference(UserInfo.class, userInfoId);
        FreeBoard freeBoard = FreeBoard.builder()
                .title(dto.title())
                .content(dto.content())
                .creator(proxyUser)
                .build();
        return freeBoardRepository.save(freeBoard).getId();
    }

    @Transactional
    public void updateFreeBoard(Long freeBoardId,FreeBoardRequestDTO dto, Long userInfoId) {
        UserInfo proxyUser = em.getReference(UserInfo.class, userInfoId);
        FreeBoard freeBoard = freeBoardRepository
                .findById(freeBoardId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글 입니다"));

        freeBoard.update(dto.title(), dto.content(), proxyUser);
    }

}
