package com.bidkita.bidkita_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "electronic_items")
@PrimaryKeyJoinColumn(name = "item_id")
public class ElectronicItem extends Item {

    private String brand;
    private String type;
    private boolean warrantyStatus;

    public ElectronicItem() {}

    public ElectronicItem(String title, String description, String condition, String imageBase64,
                          double startingPrice, String brand, String type, boolean warrantyStatus) {
        setTitle(title);
        setDescription(description);
        setCondition(condition);
        setImageBase64(imageBase64);
        setStartingPrice(startingPrice);
        setBrand(brand);
        setType(type);
        setWarrantyStatus(warrantyStatus);
    }

    @Override
    public String getCategory() {
        return "ELEKTRONIK";
    }

    @Override
    public void displayInfo() {
        System.out.println("[Elektronik] " + title + " | Brand: " + brand + " | Tipe: " + type + " | Garansi: " + warrantyStatus);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isWarrantyStatus() {
        return warrantyStatus;
    }

    public boolean isUnderWarranty() {
        return warrantyStatus;
    }

    public void setWarrantyStatus(boolean warrantyStatus) {
        this.warrantyStatus = warrantyStatus;
    }
}
