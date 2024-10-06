package com.abhilash.ecommerce.order_service.service;

import com.abhilash.ecommerce.order_service.client.OpenFeignInventoryClient;
import com.abhilash.ecommerce.order_service.dto.OrderRequestDto;
import com.abhilash.ecommerce.order_service.entity.OrderItem;
import com.abhilash.ecommerce.order_service.entity.OrderStatus;
import com.abhilash.ecommerce.order_service.entity.Orders;
import com.abhilash.ecommerce.order_service.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final ModelMapper mapper;
    private final OpenFeignInventoryClient openFeignInventoryClient;

    public List<OrderRequestDto> getAllOrders(){
        List<Orders> orders=ordersRepository.findAll();
        return orders.stream().map(order->mapper.map(order,OrderRequestDto.class)).collect(Collectors.toList());
    }

    public OrderRequestDto getOrderById(Long id){
        Orders order=ordersRepository.findById(id).orElseThrow(()->new RuntimeException("Order noty found with id "+id));
        return mapper.map(order,OrderRequestDto.class);
    }

    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        Double totalPrice=openFeignInventoryClient.reduceStocks(orderRequestDto);
        Orders order=mapper.map(orderRequestDto,Orders.class);
        for(OrderItem orderItem:order.getItems()){
            orderItem.setOrder(order);
        }
        order.setPrice(totalPrice);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        return mapper.map(ordersRepository.save(order),OrderRequestDto.class);

    }
}
