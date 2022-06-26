package com.renergy.productService.api.productCategory;

import com.renergy.productService.productCategory.ProductCategory;
import com.renergy.productService.responses.DefaultResponse;
import com.renergy.productService.responses.ImageIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/product-category")
@CrossOrigin
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(method = RequestMethod.POST, value="")
    public @ResponseBody
    DefaultResponse addNewCategory(@RequestBody ProductCategory productCategory) {
        return productCategoryService.addProductCategory(productCategory);
    }

    @RequestMapping(method = RequestMethod.GET, value="")
    public @ResponseBody
    List<ProductCategory> getAllProductCategories() {
        return productCategoryService.getAllProductCategories();
    }

    @RequestMapping(method = RequestMethod.GET,value="/details")
    public @ResponseBody
    ProductCategory getProductCategoryDetails(@RequestParam("productCategoryId") String id) {
        return productCategoryService.getProductCategoryDetails(id);
    }

    @RequestMapping(method = RequestMethod.POST,value="/image")
    public @ResponseBody
    ImageIdResponse addCategoryImage(@RequestParam("imageFile") MultipartFile multipartFile) {
        return productCategoryService.addCategoryImage(multipartFile);
    }
}
