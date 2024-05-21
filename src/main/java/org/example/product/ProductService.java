package org.example.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductEntity save(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }
    public ProductEntity findOneById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }
}
