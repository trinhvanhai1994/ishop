package com.ominext.cms.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponse {
    private Long userId;
    private List<ItemResponse> items;
    private BigDecimal amountTotal;
    private BigDecimal amountAfterDiscountTotal;
}
