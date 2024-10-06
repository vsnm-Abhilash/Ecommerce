package com.abhilash.ecommerce.inventory_service.service;

import com.abhilash.ecommerce.inventory_service.dto.ProductDto;
import com.abhilash.ecommerce.inventory_service.entity.Product;
import com.abhilash.ecommerce.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    public List<ProductDto> getAllProducts(){
        List<Product> products=productRepository.findAll();
        return products.stream().map((element) -> mapper.map(element, ProductDto.class)).collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id){
        Optional<Product> product=productRepository.findById(id);
        return product.map((element) -> mapper.map(element, ProductDto.class)).orElseThrow(
                ()->new RuntimeException("Product not found with id "+id)
        );
    }
}
