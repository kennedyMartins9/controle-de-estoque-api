package com.kennedy.controle_estoque.repository;

import com.kennedy.controle_estoque.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByProductIdOrderByMovementDateDesc(Long productId);
    void deleteByProductId(Long productId);
}
