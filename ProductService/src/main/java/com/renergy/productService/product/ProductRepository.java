package com.renergy.productService.product;

import com.renergy.productService.productCategory.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for product entity.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByProductCategory(ProductCategory productCategory);

    Optional<Product> findByUuid(String id);
}
