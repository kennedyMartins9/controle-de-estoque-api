package com.kennedy.controle_estoque.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.kennedy.controle_estoque.model.Product;
import com.kennedy.controle_estoque.model.StockMovement;
import com.kennedy.controle_estoque.dto.ProductDTO;
import com.kennedy.controle_estoque.dto.ProductUpdateDTO;
import com.kennedy.controle_estoque.dto.StockUpdateDTO;
import com.kennedy.controle_estoque.dto.StockMovementDTO;
import com.kennedy.controle_estoque.service.ProductService;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.findAll().stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        java.util.Optional<com.kennedy.controle_estoque.model.Product> opt = productService.findById(id);
        if (opt.isPresent()) {
             return ResponseEntity.ok(toProductDTO(opt.get()));
        }
        return ResponseEntity.status(404).body(Map.of("message", "Product not found."));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());
        Product created = productService.createProduct(product);
        return ResponseEntity.status(201).body(Map.of("message", "Product created successfully.", "product", toProductDTO(created)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO updateDTO) {
        Product details = new Product();
        details.setName(updateDTO.getName());
        details.setPrice(updateDTO.getPrice());

        return productService.updateProduct(id, details)
                .map(updated -> ResponseEntity.ok(Map.of("message", "Product updated successfully.", "product", toProductDTO(updated))))
                .orElse(ResponseEntity.status(404).body(Map.of("message", "Product not found.")));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateStock(@PathVariable Long id, @Valid @RequestBody StockUpdateDTO stockUpdateDTO) {
        var updated = productService.updateStock(id, stockUpdateDTO.getQuantity(), stockUpdateDTO.getOperationType());
        if (updated.isPresent()) {
            return ResponseEntity.ok(Map.of("message", "Stock updated successfully.", "product", toProductDTO(updated.get())));
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "Product not found."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if (productService.deleteById(id)) {
            return ResponseEntity.ok(Map.of("message", "Product deleted successfully."));
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "Product not found."));
        }
    }

    @GetMapping("/{id}/movements")
    public ResponseEntity<?> getStockMovements(@PathVariable Long id) {
        if (productService.findById(id).isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Product not found."));
        }
        List<StockMovementDTO> movements = productService.getMovementsByProductId(id).stream()
                .map(this::toStockMovementDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movements);
    }

    private ProductDTO toProductDTO(Product product) {
        if (product == null) return null;
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setQuantity(product.getQuantity());
        dto.setPrice(product.getPrice());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }

    private StockMovementDTO toStockMovementDTO(StockMovement movement) {
        StockMovementDTO dto = new StockMovementDTO();
        dto.setId(movement.getId());
        dto.setOperationType(movement.getOperationType());
        dto.setQuantity(movement.getQuantity());
        dto.setMovementDate(movement.getMovementDate());
        return dto;
    }
}
