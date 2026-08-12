package com.microservicenew.product_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.microservicenew.product_service.dto.ProductRequest;
import com.microservicenew.product_service.dto.ProductResponse;
import com.microservicenew.product_service.model.Product;
import com.microservicenew.product_service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

private  final ProductRepository productRepository;

public void createProduct(ProductRequest productRequest)
{
    Product product=Product.builder()
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .price(productRequest.getPrice()).build();
productRepository.save(product);
log.info("Product is Saved{}",product.getId());

}

public List<ProductResponse> getAllProduct() {
   List<Product> allproduct=productRepository.findAll();
   
   return allproduct.stream().map(product -> maptoProductResponse(product)).toList();

}

private ProductResponse maptoProductResponse(Product product) {
   return ProductResponse.builder()
   .id(product.getId())
   .name(product.getName())
   .description(product.getDescription())
   .price(product.getPrice())
   .build();

}
}