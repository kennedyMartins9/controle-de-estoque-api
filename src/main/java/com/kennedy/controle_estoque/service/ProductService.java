package com.kennedy.controle_estoque.service;

import com.kennedy.controle_estoque.model.OperationType;
import com.kennedy.controle_estoque.model.Product;
import com.kennedy.controle_estoque.model.StockMovement;
import com.kennedy.controle_estoque.repository.ProductRepository;
import com.kennedy.controle_estoque.repository.StockMovementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    public ProductService(ProductRepository productRepository, StockMovementRepository stockMovementRepository) {
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Optional<Product> updateProduct(Long id, Product details) {
        return productRepository.findById(id).map(product -> {
            product.setName(details.getName());
            product.setPrice(details.getPrice());
            return productRepository.save(product);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (productRepository.existsById(id)) {
            stockMovementRepository.deleteByProductId(id);
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Optional<Product> updateStock(Long id, int quantity, OperationType operationType) {
        Optional<Product> optProduct = productRepository.findById(id);
        if (optProduct.isEmpty()) {
            return Optional.empty();
        }

        Product product = optProduct.get();

        if (operationType == OperationType.ENTRY) {
            product.setQuantity(product.getQuantity() + quantity);
        } else if (operationType == OperationType.EXIT) {
            if (product.getQuantity() < quantity) {
                throw new IllegalArgumentException("Insufficient stock quantity.");
            }
            product.setQuantity(product.getQuantity() - quantity);
        }

        productRepository.save(product);

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setOperationType(operationType);
        movement.setQuantity(quantity);
        stockMovementRepository.save(movement);

        return Optional.of(product);
    }

    public List<StockMovement> getMovementsByProductId(Long productId) {
        return stockMovementRepository.findByProductIdOrderByMovementDateDesc(productId);
    }
}
