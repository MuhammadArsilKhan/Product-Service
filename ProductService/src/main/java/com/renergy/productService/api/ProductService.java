package com.renergy.productService.api;

import com.renergy.productService.product.Product;
import com.renergy.productService.product.ProductRepository;
import com.renergy.productService.productCategory.ProductCategory;
import com.renergy.productService.productCategory.ProductCategoryRepository;
import com.renergy.productService.requestMappers.ProductInventoryMapper;
import com.renergy.productService.responses.DefaultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public DefaultResponse addNewProduct(Product product) {

        Optional<ProductCategory> productCategory = productCategoryRepository.findById(product.getCategoryId());
        product.setProductCategory(productCategory.get());
        product.setDateAdded(new Date());
        product.setLastInventoryUpdateAt(new Date());
        product.setUuid(UUID.randomUUID().toString());
        productRepository.save(product);
        return new DefaultResponse("S001", "Product added successfully");
    }

    public Product getProductDetails(String id) {

        LOGGER.info("Getting product details with id : ", id);
        Optional<Product> product = productRepository.findByUuid(id);
        return product.get();
    }

    public DefaultResponse updateProductInventory(ProductInventoryMapper productInventoryMapper) {

        LOGGER.info("Updating product inventory");
        Optional<Product> product = productRepository.findById(productInventoryMapper.getId());
        product.get().setInventory(product.get().getInventory() + productInventoryMapper.getInventory());
        product.get().setLastInventoryUpdateAt(new Date());

        productRepository.save(product.get());
        return new DefaultResponse("S001", "Inventory updated successfully");
    }



}
