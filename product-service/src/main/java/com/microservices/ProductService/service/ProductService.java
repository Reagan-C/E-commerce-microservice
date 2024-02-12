package com.microservices.ProductService.service;

import com.microservices.ProductService.dto.ProductRequest;
import com.microservices.ProductService.dto.ProductResponse;
import java.util.List;

public interface ProductService {

    void createProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();
}
