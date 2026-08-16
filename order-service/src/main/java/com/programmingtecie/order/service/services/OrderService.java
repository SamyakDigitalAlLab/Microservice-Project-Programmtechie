package com.programmingtecie.order.service.services;

import java.util.Arrays;
import java.util.UUID;
import java.util.List;

import com.programmingtecie.order.service.dto.InventoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmingtecie.order.service.dto.OrderLineItemdto;
import com.programmingtecie.order.service.dto.OrderRequest;
import com.programmingtecie.order.service.model.Order;
import com.programmingtecie.order.service.model.OrderLineItem;
import com.programmingtecie.order.service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;


    public void placeOrder(OrderRequest orderRequest){
Order order=new Order();
order.setOrderNumber(UUID.randomUUID().toString());

List<OrderLineItem> orderLineItem= orderRequest.getOrderLineItemdto().
stream()
.map(this::maptoDTO)
.toList();

order.setOrderLineItem(orderLineItem);


List<String> skuCode=order.getOrderLineItem().stream()
        .map(orderLineItems -> orderLineItems.getSkucod())
        .toList();

        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder
                        .queryParam("skuCode", skuCode)
                        .build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();


       boolean allProductsInStcok= Arrays.stream(inventoryResponses)
                .allMatch(InventoryResponse::isInStock);

if(allProductsInStcok){
    orderRepository.save(order);
}else{
    throw  new IllegalArgumentException("Product is not in the stock ,plz try again later");
}
    }



 private OrderLineItem maptoDTO(OrderLineItemdto orderLineItemdto) {
    OrderLineItem orderLineItem = new OrderLineItem();
    orderLineItem.setPrice(orderLineItemdto.getPrice());
    orderLineItem.setQuantity(orderLineItemdto.getQuantity());
    orderLineItem.setSkucod(orderLineItemdto.getSkucod()); // adjust method name if needed
    return orderLineItem;
}
   
}
