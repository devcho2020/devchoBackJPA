package com.devcho.devchobackjpa.repository.tipboard;

import com.devcho.devchobackjpa.domain.TipBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TipBoardRepositoryCustom {
    Page<TipBoard> searchTipBoards(String searchValue, String selectedOption, Pageable pageable);
    TipBoard findByIdWithCreatorAndUpdater(Long id);
}
