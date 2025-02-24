package com.example.sushiorderapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sushiorderapi.model.entity.MenuCategory;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    List<MenuCategory> findByActiveOrderByDisplayOrderAsc(Boolean active);
    boolean existsByName(String name);
}