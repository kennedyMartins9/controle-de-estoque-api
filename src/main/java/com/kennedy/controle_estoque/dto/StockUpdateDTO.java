package com.kennedy.controle_estoque.dto;

import com.kennedy.controle_estoque.model.OperationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockUpdateDTO {
    @Min(value = 1, message = "Quantity must be greater than zero.")
    private int quantity;

    @NotNull(message = "Operation type is required (ENTRY or EXIT).")
    private OperationType operationType;
}
