package com.bidkita.bidkita_backend.repository;

import com.bidkita.bidkita_backend.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {
}