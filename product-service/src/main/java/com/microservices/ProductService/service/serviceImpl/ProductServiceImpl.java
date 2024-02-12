package com.microservices.ProductService.service.serviceImpl;

import com.microservices.ProductService.dto.ProductRequest;
import com.microservices.ProductService.dto.ProductResponse;
import com.microservices.ProductService.model.Product;
import com.microservices.ProductService.repository.ProductRepository;
import com.microservices.ProductService.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public void createProduct(ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        repository.save(product);
        log.info("Product {} saved", product.getId());
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = repository.findAll();

        return products.stream().map(this::mapToProduct)
                .toList();
    }

    private ProductResponse mapToProduct(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

}
