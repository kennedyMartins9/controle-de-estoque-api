package com.kennedy.controle_estoque.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * DTO exclusivo para atualizações cadastrais (PUT).
 * Não expõe 'quantity' para garantir que o estoque só seja alterado via movimentação (PATCH).
 */
@Getter
@Setter
public class ProductUpdateDTO {

    @NotBlank(message = "Product name is required.")
    private String name;

    @Min(value = 0, message = "Price cannot be negative.")
    private BigDecimal price;
}
