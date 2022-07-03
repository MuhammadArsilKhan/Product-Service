package com.renergy.productService.api;

import com.renergy.productService.product.Product;
import com.renergy.productService.product.ProductRepository;
import com.renergy.productService.productCategory.ProductCategory;
import com.renergy.productService.productCategory.ProductCategoryRepository;
import com.renergy.productService.requestMappers.ProductInventoryMapper;
import com.renergy.productService.responses.DefaultResponse;
import com.renergy.productService.responses.DownloadDocumentResponse;
import com.renergy.productService.responses.ImageIdResponse;
import com.renergy.productService.responses.UploadFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Value("${renergy.images.folder}")
    private String renergy_images_folder;

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


    public ImageIdResponse addProductImage(MultipartFile multipartFile) {

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

    public UploadFileResponse uploadProductManual(MultipartFile file)
    {
        UploadFileResponse response = null;
        String fileName = UUID.randomUUID().toString();
        String fileUrl;
        try {

            fileName = fileName + ".pdf";
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

    public ResponseEntity downloadDocuments(String productId) throws IOException {

        try {

            Optional<Product> product = productRepository.findByUuid(productId);
            if(product.isPresent()) {
                File file = new File(product.get().getManualPath());
                byte[] content = Files.readAllBytes(file.toPath());
                DownloadDocumentResponse downloadDocumentResponse = new DownloadDocumentResponse(content, product.get().getProductName()+".pdf");
                return new ResponseEntity(downloadDocumentResponse, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity("No product found", HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }
    }

}
