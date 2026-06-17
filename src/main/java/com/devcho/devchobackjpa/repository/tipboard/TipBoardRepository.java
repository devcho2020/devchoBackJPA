package com.devcho.devchobackjpa.repository.tipboard;

import com.devcho.devchobackjpa.domain.TipBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipBoardRepository extends JpaRepository<TipBoard, Long>, TipBoardRepositoryCustom {
}
