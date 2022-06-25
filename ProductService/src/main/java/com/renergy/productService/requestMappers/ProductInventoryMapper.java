package com.renergy.productService.requestMappers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class ProductInventoryMapper {
    private Long id;

    private int inventory;
}
