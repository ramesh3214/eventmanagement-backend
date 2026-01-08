package com.backend.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestdto {

    private String order_id;
    private Double order_amount;
    private String order_currency;
    private CustomerDetails customer_details;

    // ❌ REMOVE this
    // private String return_url;

    // ✅ ADD THIS
    private OrderMeta order_meta;

}



