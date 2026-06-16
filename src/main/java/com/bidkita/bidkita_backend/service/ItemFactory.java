package com.bidkita.bidkita_backend.service;

import com.bidkita.bidkita_backend.dto.request.CreateAuctionRequestDTO;
import com.bidkita.bidkita_backend.model.*;
import org.springframework.stereotype.Component;

@Component
public class ItemFactory {

    public Item create(CreateAuctionRequestDTO dto) {
        String category = dto.getCategory() == null ? "" : dto.getCategory().toUpperCase();
        return switch (category) {
            case "ELEKTRONIK" -> {
                ElectronicItem item = new ElectronicItem();
                item.setTitle(dto.getTitle());
                item.setDescription(dto.getDescription());
                item.setCondition(dto.getCondition());
                item.setImageBase64(dto.getImageBase64());
                item.setStartingPrice(dto.getStartingPrice());
                item.setBrand(dto.getBrand());
                item.setType(dto.getType());
                item.setWarrantyStatus(Boolean.TRUE.equals(dto.getWarrantyStatus()));
                yield item;
            }
            case "FASHION" -> {
                FashionItem item = new FashionItem();
                item.setTitle(dto.getTitle());
                item.setDescription(dto.getDescription());
                item.setCondition(dto.getCondition());
                item.setImageBase64(dto.getImageBase64());
                item.setStartingPrice(dto.getStartingPrice());
                item.setBrand(dto.getBrand());
                item.setSize(dto.getSize());
                item.setMaterial(dto.getMaterial());
                yield item;
            }
            case "BUKU" -> {
                BookItem item = new BookItem();
                item.setTitle(dto.getTitle());
                item.setDescription(dto.getDescription());
                item.setCondition(dto.getCondition());
                item.setImageBase64(dto.getImageBase64());
                item.setStartingPrice(dto.getStartingPrice());
                item.setAuthor(dto.getAuthor());
                item.setPublisher(dto.getPublisher());
                item.setPublishYear(dto.getPublishYear() != null ? dto.getPublishYear() : 0);
                yield item;
            }
            case "KENDARAAN" -> {
                VehicleItem item = new VehicleItem();
                item.setTitle(dto.getTitle());
                item.setDescription(dto.getDescription());
                item.setCondition(dto.getCondition());
                item.setImageBase64(dto.getImageBase64());
                item.setStartingPrice(dto.getStartingPrice());
                item.setBrand(dto.getBrand());
                item.setYear(dto.getYear() != null ? dto.getYear() : 0);
                item.setMileage(dto.getMileage() != null ? dto.getMileage() : 0);
                item.setPlatNumber(dto.getPlatNumber());
                yield item;
            }
            default -> throw new IllegalArgumentException("Kategori tidak valid: " + dto.getCategory());
        };
    }
}
