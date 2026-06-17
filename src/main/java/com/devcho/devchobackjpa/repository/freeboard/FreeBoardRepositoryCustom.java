package com.devcho.devchobackjpa.repository.freeboard;

import com.devcho.devchobackjpa.domain.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FreeBoardRepositoryCustom {
    Page<FreeBoard> searchFreeBoard(String searchValue, String selectedOption, Pageable pageable);
}
