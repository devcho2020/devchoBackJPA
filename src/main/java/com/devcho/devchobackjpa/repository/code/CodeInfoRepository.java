package com.devcho.devchobackjpa.repository.code;

import com.devcho.devchobackjpa.domain.CodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodeInfoRepository extends JpaRepository<CodeInfo, Long>, CodeInfoRepositoryCustom {
    Optional<CodeInfo> findByCode(String code);

    boolean existsByCode(String code);
}
