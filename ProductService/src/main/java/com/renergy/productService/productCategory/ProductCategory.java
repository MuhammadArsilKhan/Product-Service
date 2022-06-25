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

    @NonNull
    private String categoryDescription;

    private String uuid;

    @OneToMany(mappedBy = "productCategory" , fetch = FetchType.LAZY)
    private List<Product> productList = new ArrayList<>();
}
