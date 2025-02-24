package com.example.sushiorderapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sushiorderapi.model.entity.SushiItem;
import com.example.sushiorderapi.repository.SushiItemRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SushiItemService {

    @Autowired
    private SushiItemRepository sushiItemRepository;

    public List<SushiItem> getAllItems() {
        return sushiItemRepository.findAll();
    }

    public List<SushiItem> getAvailableItems() {
        return sushiItemRepository.findByAvailableTrue();
    }

    @Transactional
    public SushiItem createItem(SushiItem item) {
        return sushiItemRepository.save(item);
    }

    @Transactional
    public SushiItem updateItem(Long id, SushiItem item) {
        SushiItem existingItem = sushiItemRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Sushi item not found"));
        
        existingItem.setName(item.getName());
        existingItem.setDescription(item.getDescription());
        existingItem.setPrice(item.getPrice());
        existingItem.setImageUrl(item.getImageUrl());
        existingItem.setAvailable(item.getAvailable());
        
        return sushiItemRepository.save(existingItem);
    }

    @Transactional
    public SushiItem updateAvailability(Long id, Boolean available) {
        SushiItem item = sushiItemRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Sushi item not found"));
        
        item.setAvailable(available);
        return sushiItemRepository.save(item);
    }
}