package com.abhilash.ecommerce.order_service.contoller;

import com.abhilash.ecommerce.order_service.dto.OrderRequestDto;
import com.abhilash.ecommerce.order_service.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders(){
        return ResponseEntity.ok(ordersService.getAllOrders());
    }

    @GetMapping("/{Id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long Id){
        return ResponseEntity.ok(ordersService.getOrderById(Id));
    }

    @GetMapping("/helloOrders")
    public String helloOrders(){
        return "Hello from Order Service";
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        return ResponseEntity.ok(ordersService.createOrder(orderRequestDto));
    }

}
