package com.devcho.devchobackjpa.repository.code;

import com.devcho.devchobackjpa.domain.CodeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CodeInfoRepositoryCustom {
    Page<CodeInfo> searchCodeInfo(String searchValue, String selectedOption, Pageable pageable);
    CodeInfo findByCodeWithCreatorAndUpdater(String code);
}
