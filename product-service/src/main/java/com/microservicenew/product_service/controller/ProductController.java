package com.microservicenew.product_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.microservicenew.product_service.dto.ProductRequest;
import com.microservicenew.product_service.dto.ProductResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microservicenew.product_service.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
  public void createProduct(@RequestBody ProductRequest productRequest){

productService.createProduct(productRequest);
  }

@GetMapping
@ResponseStatus(HttpStatus.OK)
public List<ProductResponse> getAllProduct() {
  return productService.getAllProduct();
}



}
