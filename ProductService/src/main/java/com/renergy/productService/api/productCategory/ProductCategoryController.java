package com.renergy.productService.api.productCategory;

import com.renergy.productService.productCategory.ProductCategory;
import com.renergy.productService.responses.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
