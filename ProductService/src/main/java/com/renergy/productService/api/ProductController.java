package com.renergy.productService.api;

import com.renergy.productService.product.Product;
import com.renergy.productService.productCategory.ProductCategory;
import com.renergy.productService.requestMappers.ProductInventoryMapper;
import com.renergy.productService.responses.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.POST, value="")
    public @ResponseBody
    DefaultResponse addNewProduct(@RequestBody Product product) {
        return productService.addNewProduct(product);
    }

    @RequestMapping(method = RequestMethod.GET, value="")
    public @ResponseBody
    Product getProductDetails(@RequestParam("productId") String id) {
        return productService.getProductDetails(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value="")
    public @ResponseBody
    DefaultResponse updateInventory(@RequestBody ProductInventoryMapper productInventoryMapper) {
        return productService.updateProductInventory(productInventoryMapper);
    }

}
