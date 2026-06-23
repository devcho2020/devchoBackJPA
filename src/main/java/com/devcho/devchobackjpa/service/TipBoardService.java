package com.devcho.devchobackjpa.service;

import com.devcho.devchobackjpa.domain.TipBoard;
import com.devcho.devchobackjpa.domain.UserInfo;
import com.devcho.devchobackjpa.dto.page.PageResponse;
import com.devcho.devchobackjpa.dto.tipboard.TipBoardRequestDTO;
import com.devcho.devchobackjpa.dto.tipboard.TipBoardResponseDTO;
import com.devcho.devchobackjpa.repository.tipboard.TipBoardRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TipBoardService {

    @PersistenceContext
    private EntityManager em;
    private final TipBoardRepository tipBoardRepository;

    public PageResponse<TipBoardResponseDTO> getTipBoardList(int page, int size, String selectedOption, String searchValue) {

        Pageable pageable = PageRequest.of(page, size);

        Page<TipBoard> result = tipBoardRepository.searchTipBoards(searchValue, selectedOption, pageable);

        List<TipBoardResponseDTO> content = result.getContent().stream()
                .map(TipBoardResponseDTO::from)
                .collect(Collectors.toList());

        return new PageResponse<>(content, result.getTotalPages(), result.getTotalElements());
    }

    public TipBoardResponseDTO findTipBoardById(Long tipBoardId) {
        TipBoard tipBoard = tipBoardRepository.findByIdWithCreatorAndUpdater(tipBoardId);
        return TipBoardResponseDTO.from(tipBoard);
    }

    @Transactional
    public Long saveTipBoard(TipBoardRequestDTO dto, Long userInfoId) {
        UserInfo proxyUser = em.getReference(UserInfo.class, userInfoId);
        TipBoard tipBoard = TipBoard.builder()
                .title(dto.title())
                .content(dto.content())
                .creator(proxyUser)
                .build();
        return tipBoardRepository.save(tipBoard).getId();
    }

    @Transactional
    public void updateTipBoard(Long tipBoardId, TipBoardRequestDTO dto, Long userInfoId) {
        UserInfo proxyUser = em.getReference(UserInfo.class, userInfoId);
        TipBoard tipBoard = tipBoardRepository
                .findById(tipBoardId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글 입니다"));

        tipBoard.update(dto.title(), dto.content(), proxyUser);
    }
}
