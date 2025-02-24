package com.example.sushiorderapi.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sushiorderapi.model.dto.AdminDashboardResponse;
import com.example.sushiorderapi.model.dto.OrderStatistics;
import com.example.sushiorderapi.model.entity.Order;
import com.example.sushiorderapi.model.entity.User;
import com.example.sushiorderapi.repository.OrderRepository;
import com.example.sushiorderapi.repository.UserRepository;

@Service
public class AdminService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public AdminDashboardResponse getDashboardInfo() {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime tomorrow = today.plusDays(1);

        AdminDashboardResponse response = new AdminDashboardResponse();
        response.setTotalOrders(orderRepository.countByOrderTimeBetween(today, tomorrow));
        response.setTotalRevenue(orderRepository.sumRevenueBetween(today, tomorrow));
        response.setPendingOrders(orderRepository.countByStatus("PENDING"));
        response.setActiveUsers(userRepository.countByEnabled(true));

        return response;
    }

    public OrderStatistics getOrderStatistics(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        OrderStatistics statistics = new OrderStatistics();
        statistics.setTotalOrders(orderRepository.countByOrderTimeBetween(start, end));
        statistics.setTotalRevenue(orderRepository.sumRevenueBetween(start, end));
        statistics.setAverageOrderValue(orderRepository.averageOrderValueBetween(start, end));
        statistics.setPopularItems(orderRepository.findPopularItemsBetween(start, end));

        return statistics;
    }

    public Page<User> getAllUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    public User toggleUserStatus(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(!user.isEnabled());
        return userRepository.save(user);
    }

    @Transactional
    public void bulkUpdateOrderStatus(List<Long> orderIds, String status) {
        List<Order> orders = orderRepository.findAllById(orderIds);
        orders.forEach(order -> order.setStatus(status));
        orderRepository.saveAll(orders);
    }
}