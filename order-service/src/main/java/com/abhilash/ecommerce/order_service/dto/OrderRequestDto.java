package com.abhilash.ecommerce.order_service.dto;

import com.abhilash.ecommerce.order_service.entity.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private Long id;
    private Double price;
    private List<OrderItem> items;
}
