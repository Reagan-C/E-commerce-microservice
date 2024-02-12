package com.microservices.inventoryservice.service;

public interface InventoryService {
    boolean isInStock(String skuCode);
}
