package com.microservices.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.orderservice.dto.OrderLineItemsDto;
import com.microservices.orderservice.dto.OrderRequest;
import com.microservices.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class OrderServiceApplicationTests {

    @Container
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:5.6");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
    }

    @Test
    void shouldCreateOrder() throws Exception {
        OrderRequest orderRequest = getOrderRequest();
        String orderRequestAsString = objectMapper.writeValueAsString(orderRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderRequestAsString))
                .andExpect(status().isCreated());

        Assertions.assertEquals(1, orderRepository.findAll().size());
    }

    private OrderRequest getOrderRequest() {
        OrderLineItemsDto orderLineItemsDto = new OrderLineItemsDto("IPhone14",
                BigDecimal.valueOf(2100), 1);

        List<OrderLineItemsDto> LineItemsDtoList = new ArrayList<>();
        LineItemsDtoList.add(orderLineItemsDto);

        return OrderRequest.builder()
                .orderLineItemsDtoList(LineItemsDtoList)
                .build();
    }
}
