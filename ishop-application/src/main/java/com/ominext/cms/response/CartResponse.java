package com.ominext.cms.response;

import com.ominext.cms.model.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponse {
    private Long userId;
    private List<Product> products;
    private BigDecimal amount;
    private BigDecimal amountAfterDiscount;
}
