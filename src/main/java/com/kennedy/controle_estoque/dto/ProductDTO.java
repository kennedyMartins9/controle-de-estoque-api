package com.kennedy.controle_estoque.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Long id;

    @NotBlank(message = "Product name is required.")
    private String name;

    @Min(value = 0, message = "Quantity cannot be negative.")
    private int quantity;

    @Min(value = 0, message = "Price cannot be negative.")
    private java.math.BigDecimal price;

    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime updatedAt;
}
