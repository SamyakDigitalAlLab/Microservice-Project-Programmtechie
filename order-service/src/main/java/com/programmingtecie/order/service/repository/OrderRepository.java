package com.programmingtecie.order.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmingtecie.order.service.model.Order;



public interface  OrderRepository extends JpaRepository<Order, Long>{

}
