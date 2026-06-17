package com.devcho.devchobackjpa.repository.freeboard;

import com.devcho.devchobackjpa.domain.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long>, FreeBoardRepositoryCustom {
}
