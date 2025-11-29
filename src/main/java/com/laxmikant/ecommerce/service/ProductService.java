package com.laxmikant.ecommerce.service;

import com.laxmikant.ecommerce.entity.Product;
import com.laxmikant.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product updated) {
        return productRepository.findById(id)
                .map(p -> {
                    p.setName(updated.getName());
                    p.setDescription(updated.getDescription());
                    p.setPrice(updated.getPrice());
                    p.setStock(updated.getStock());
                    return productRepository.save(p);
                })
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
