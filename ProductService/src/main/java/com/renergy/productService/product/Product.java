package com.renergy.productService.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.renergy.productService.productCategory.ProductCategory;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_product")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String productName;

    @NonNull
    private String productDescription;

    @NonNull
    private double price;

    @NonNull
    private Date dateAdded;

    private int inventory;

    private Date lastInventoryUpdateAt;

    private String uuid;

    private String voltage;

    private String power;

    private String efficiency;

    private String imageName;

    private String manualPath;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_category_id",referencedColumnName = "id")
    private ProductCategory productCategory;

    @Transient
    private Long categoryId;
}
