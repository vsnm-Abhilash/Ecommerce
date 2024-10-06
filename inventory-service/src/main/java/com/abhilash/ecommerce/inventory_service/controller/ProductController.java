package com.abhilash.ecommerce.inventory_service.controller;

import com.abhilash.ecommerce.inventory_service.dto.ProductDto;
import com.abhilash.ecommerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> products=productService.getAllProducts();
        return ResponseEntity.ok(products);
    }


    @GetMapping("/{Id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long Id){
        ProductDto product=productService.getProductById(Id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/fetchOrders")
    public String fetchFromOrdersService(){
        ServiceInstance serviceInstance = discoveryClient.getInstances("order-service").get(0);

        return restClient.get().uri(serviceInstance.getUri()+"/orders/core/helloOrders").retrieve()
                .body(String.class);
    }
}
