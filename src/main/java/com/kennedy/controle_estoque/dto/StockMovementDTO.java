package com.kennedy.controle_estoque.dto;

import com.kennedy.controle_estoque.model.OperationType;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * DTO de resposta para o histórico de movimentações de estoque.
 */
@Getter
@Setter
public class StockMovementDTO {
    private Long id;
    private OperationType operationType;
    private int quantity;
    private LocalDateTime movementDate;
}
