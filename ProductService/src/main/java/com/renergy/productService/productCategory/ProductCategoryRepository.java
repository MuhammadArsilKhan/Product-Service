package com.renergy.productService.productCategory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    Optional<ProductCategory> findByUuid(String uuid);

    List<ProductCategory> findAllByParentCategory(ProductCategory productCategory);
}
