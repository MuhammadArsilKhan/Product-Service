package com.renergy.productService.api.productCategory;

import com.renergy.productService.product.Product;
import com.renergy.productService.product.ProductRepository;
import com.renergy.productService.productCategory.ProductCategory;
import com.renergy.productService.productCategory.ProductCategoryRepository;
import com.renergy.productService.responses.DefaultResponse;
import com.renergy.productService.responses.ImageIdResponse;
import com.renergy.productService.responses.ParentProduct;
import com.renergy.productService.responses.UploadFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Value("${renergy.images.folder}")
    private String renergy_images_folder;

    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public DefaultResponse addProductCategory(ProductCategory productCategory) {

        LOGGER.info("Adding new product category");
        productCategory.setUuid(UUID.randomUUID().toString());
        Optional<ProductCategory> parentProductCategory = productCategoryRepository.findByUuid(productCategory.getParentCategoryUUID());
        productCategory.setParentCategory(parentProductCategory.get());
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

    public ImageIdResponse addCategoryImage(MultipartFile multipartFile) {

        String name = UUID.randomUUID().toString();
        //Saving image in Folder
        uploadImage(multipartFile, name);

        ImageIdResponse imageIdResponse = new ImageIdResponse();
        imageIdResponse.setImageUuid(name);
        return imageIdResponse;

    }

    private UploadFileResponse uploadImage(MultipartFile file, String fileName)
    {
        UploadFileResponse response = null;

        String fileUrl;
        try {

            fileName = fileName + ".jpg";
            Path fileStorageLocation= Paths.get(renergy_images_folder);
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            fileUrl = (fileStorageLocation.toString())+"/"+fileName;
            System.out.println("file path "+ fileUrl);
            response = new UploadFileResponse("Success",fileUrl,fileName);
            return response;

        }
        catch (Exception e){
            System.out.println(e);
            return response = new UploadFileResponse("Failure","","");
        }

    }

    public List<ProductCategory> getAllMainProductCategories() {
        List<ProductCategory> productCategories = productCategoryRepository.findAllByParentCategory(null);
        List<ParentProduct> parentProducts = new ArrayList<>();
        for(ProductCategory productCategory : productCategories) {
            ParentProduct parentProduct = new ParentProduct(productCategory.getCategoryName(), productCategory.getUuid());
            parentProducts.add(parentProduct);
        }
        return productCategories;
    }

    public DefaultResponse addParentProduct(String productName) {

        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName(productName);
        productCategory.setUuid(UUID.randomUUID().toString());
        productCategory.setParentCategory(null);
        productCategoryRepository.save(productCategory);
        return new DefaultResponse("200", "Product added successfully");
    }
}
