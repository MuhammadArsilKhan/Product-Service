package com.renergy.productService.api.productCategory;

import com.renergy.productService.product.Product;
import com.renergy.productService.product.ProductRepository;
import com.renergy.productService.productCategory.ProductCategory;
import com.renergy.productService.productCategory.ProductCategoryRepository;
import com.renergy.productService.responses.DefaultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public DefaultResponse addProductCategory(ProductCategory productCategory) {

        LOGGER.info("Adding new product category");
        productCategory.setUuid(UUID.randomUUID().toString());
        productCategoryRepository.save(productCategory);
        return new DefaultResponse("S001", "Product category added successfully");
    }

    public List<ProductCategory> getAllProductCategories() {
        LOGGER.info("Getting list of all product categories");
        return productCategoryRepository.findAll();
    }

    public ProductCategory getProductCategoryDetails(String id) {
        LOGGER.info("Getting details of product category");
        Optional<ProductCategory> productCategory = productCategoryRepository.findByUuid(id);
        List<Product> products = productRepository.findAllByProductCategory(productCategory.get());
        productCategory.get().setProductList(products);
        return productCategory.get();
    }
}
