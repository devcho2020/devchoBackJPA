package com.devcho.devchobackjpa.repository.code;

import com.devcho.devchobackjpa.domain.CodeInfo;

import java.util.List;

public interface CodeInfoRepositoryCustom {
    List<CodeInfo> searchCodeInfo(String selectedOption, boolean useYn);
    CodeInfo findByCodeWithCreatorAndUpdater(String code);
}
