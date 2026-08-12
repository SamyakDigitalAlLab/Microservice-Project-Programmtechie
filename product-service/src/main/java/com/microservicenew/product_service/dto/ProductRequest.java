package com.microservicenew.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ProductRequest
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
     
    private String name;
    private String description;
    private String price;

}
