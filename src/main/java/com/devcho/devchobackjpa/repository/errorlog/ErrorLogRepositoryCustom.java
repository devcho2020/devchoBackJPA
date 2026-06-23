package com.devcho.devchobackjpa.repository.errorlog;

import com.devcho.devchobackjpa.domain.ErrorLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ErrorLogRepositoryCustom {
    Page<ErrorLog> searchErrorLogs(String selectedOption, String searchValue, Pageable pageable);
    ErrorLog findErrorLogByIdWithCreatorAndUpdater(Long id);
}
