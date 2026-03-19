package com.kennedy.controle_estoque.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {
    
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Product name is required.")
    private String name;

    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime updatedAt;


    @Min(value = 0, message = "Quantity cannot be negative.")
    private int quantity;

    @Min(value = 0, message = "Price cannot be negative.")
    private java.math.BigDecimal price;

    @jakarta.persistence.PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        updatedAt = createdAt;
    }

    @jakarta.persistence.PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now();
    }
}
