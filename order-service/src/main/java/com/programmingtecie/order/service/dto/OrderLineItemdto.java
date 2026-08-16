    package com.programmingtecie.order.service.dto;

    import java.math.BigDecimal;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class OrderLineItemdto {
    private Long id;
    private String skucod;
    private BigDecimal price;
    private  Integer quantity;
    }
