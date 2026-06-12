package com.bidkita.bidkita_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "fashion_items")
@PrimaryKeyJoinColumn(name = "item_id")
public class FashionItem extends Item {

    private String brand;
    private String size;
    private String material;

    public FashionItem() {}

    public FashionItem(String title, String description, String condition, String imageBase64,
                       double startingPrice, String brand, String size, String material) {
        setTitle(title);
        setDescription(description);
        setCondition(condition);
        setImageBase64(imageBase64);
        setStartingPrice(startingPrice);
        setBrand(brand);
        setSize(size);
        setMaterial(material);
    }

    @Override
    public String getCategory() {
        return "FASHION";
    }

    @Override
    public void displayInfo() {
        System.out.println("[Fashion] " + title + " | Brand: " + brand + " | Size: " + size + " | Material: " + material);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
