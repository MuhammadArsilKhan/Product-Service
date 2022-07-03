package com.renergy.productService.productCategory;

import com.renergy.productService.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_product_category")
@NoArgsConstructor
@Getter @Setter
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String categoryName;

    private String categoryDescription;

    private String uuid;

    private String imageName;

    private String parentCategoryUUID;

    @OneToMany(mappedBy = "productCategory" , fetch = FetchType.LAZY)
    private List<Product> productList = new ArrayList<>();

    @ManyToOne
    private ProductCategory parentCategory;

    @OneToMany(mappedBy = "parentCategory" , fetch = FetchType.EAGER)
    private List<ProductCategory> subCategories = new ArrayList<>();


}
