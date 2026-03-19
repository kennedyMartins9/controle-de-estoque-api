package com.kennedy.controle_estoque.repository;

import com.kennedy.controle_estoque.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
