package com.kennedy.controle_estoque.service;

import com.kennedy.controle_estoque.model.OperationType;
import com.kennedy.controle_estoque.model.Product;
import com.kennedy.controle_estoque.model.StockMovement;
import com.kennedy.controle_estoque.repository.ProductRepository;
import com.kennedy.controle_estoque.repository.StockMovementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockMovementRepository stockMovementRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setQuantity(10);
        product.setPrice(new BigDecimal("100.00"));
    }

    @Test
    void testEntryStock() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(stockMovementRepository.save(any(StockMovement.class))).thenReturn(new StockMovement());

        Optional<Product> updated = productService.updateStock(1L, 5, OperationType.ENTRY);

        assertTrue(updated.isPresent());
        assertEquals(15, updated.get().getQuantity());
        verify(productRepository, times(1)).save(product);
        verify(stockMovementRepository, times(1)).save(any(StockMovement.class));
    }

    @Test
    void testExitStockSuccess() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(stockMovementRepository.save(any(StockMovement.class))).thenReturn(new StockMovement());

        Optional<Product> updated = productService.updateStock(1L, 5, OperationType.EXIT);

        assertTrue(updated.isPresent());
        assertEquals(5, updated.get().getQuantity());
        verify(productRepository, times(1)).save(product);
        verify(stockMovementRepository, times(1)).save(any(StockMovement.class));
    }

    @Test
    void testExitStockInsufficientQuantity() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.updateStock(1L, 15, OperationType.EXIT);
        });

        assertEquals("Insufficient stock quantity.", exception.getMessage());
        verify(productRepository, never()).save(any(Product.class));
        verify(stockMovementRepository, never()).save(any(StockMovement.class));
    }

    @Test
    void testUpdateProductDoesNotChangeQuantity() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product details = new Product();
        details.setName("Updated Name");
        details.setPrice(new BigDecimal("200.00"));

        Optional<Product> updated = productService.updateProduct(1L, details);

        assertTrue(updated.isPresent());
        assertEquals(10, updated.get().getQuantity()); // quantidade deve permanecer inalterada
        assertEquals("Updated Name", updated.get().getName());
    }
}
