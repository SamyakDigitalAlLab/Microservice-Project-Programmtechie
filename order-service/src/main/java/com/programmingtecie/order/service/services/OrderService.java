package com.programmingtecie.order.service.services;

import java.util.UUID;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmingtecie.order.service.dto.OrderLineItemdto;
import com.programmingtecie.order.service.dto.OrderRequest;
import com.programmingtecie.order.service.model.Order;
import com.programmingtecie.order.service.model.OrderLineItem;
import com.programmingtecie.order.service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
Order order=new Order();
order.setOrderNumber(UUID.randomUUID().toString());

List<OrderLineItem> orderLineItem= orderRequest.getOrderLineItemdto().
stream()
.map(this::maptoDTO)
.toList();

order.setOrderLineItem(orderLineItem);
orderRepository.save(order);
    }



 private OrderLineItem maptoDTO(OrderLineItemdto orderLineItemdto) {
    OrderLineItem orderLineItem = new OrderLineItem();
    orderLineItem.setPrice(orderLineItemdto.getPrice());
    orderLineItem.setQuantity(orderLineItemdto.getQuantity());
    orderLineItem.setSkucod(orderLineItemdto.getSkucod()); // adjust method name if needed
    return orderLineItem;
}
   
}
