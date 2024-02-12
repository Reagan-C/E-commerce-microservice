package com.microservices.orderservice.service;

import com.microservices.orderservice.dto.OrderRequest;

public interface OrderService {
    void placeOrder(OrderRequest orderRequest);
}
