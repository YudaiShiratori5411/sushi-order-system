package com.example.sushiorderapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sushiorderapi.model.dto.OrderRequestDTO;
import com.example.sushiorderapi.model.entity.Order;
import com.example.sushiorderapi.model.entity.OrderItem;
import com.example.sushiorderapi.model.entity.SushiItem;
import com.example.sushiorderapi.model.entity.User;
import com.example.sushiorderapi.repository.OrderRepository;
import com.example.sushiorderapi.repository.SushiItemRepository;
import com.example.sushiorderapi.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private SushiItemRepository sushiItemRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Order createOrder(OrderRequestDTO orderRequest, Long userId) { // ユーザーIDを追加
        // ユーザー情報の取得
        User customer = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // 注文エンティティの作成
        Order order = new Order();
        order.setCustomer(customer);
        order.setPhoneNumber(orderRequest.getPhoneNumber());
        order.setStatus("PENDING");

        // OrderItemの作成と関連付け
        List<OrderItem> orderItems = orderRequest.getItems().stream()
            .map(itemDTO -> {
                // 商品情報の取得
                SushiItem sushiItem = sushiItemRepository.findById(itemDTO.getSushiItemId())
                    .orElseThrow(() -> new EntityNotFoundException("SushiItem not found"));

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setSushiItem(sushiItem);
                orderItem.setQuantity(itemDTO.getQuantity());
                orderItem.setPrice(sushiItem.getPrice());
                return orderItem;
            })
            .collect(Collectors.toList());

        order.setItems(orderItems);
        
        // 合計金額の計算
        double totalAmount = orderItems.stream()
            .mapToDouble(item -> item.getPrice() * item.getQuantity())
            .sum();
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Transactional
    public Order updateOrderStatus(Long id, String status) {
        Order order = getOrderById(id);
        order.setStatus(status.toUpperCase());
        return orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(Long id) {
        Order order = getOrderById(id);
        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }
}