package org.example.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductColorSizeMappingRepository extends JpaRepository<ProductColorSizeMapping, Long> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM  product_color_size_mapping WHERE product_id = ?1")
    ProductColorSizeMapping findByProductId(Long id);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM  product_color_size_mapping WHERE product_id in ?1")
    List<ProductColorSizeMapping> findAllByProductIds(List<Long> productIds);
}
