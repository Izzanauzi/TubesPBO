package com.bidkita.bidkita_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicle_items")
@PrimaryKeyJoinColumn(name = "item_id")
public class VehicleItem extends Item {

    private String brand;
    private int year;
    private int mileage;
    private String platNumber;

    public VehicleItem() {}

    public VehicleItem(String title, String description, String condition, String imageBase64,
                       double startingPrice, String brand, int year, int mileage, String platNumber) {
        setTitle(title);
        setDescription(description);
        setCondition(condition);
        setImageBase64(imageBase64);
        setStartingPrice(startingPrice);
        setBrand(brand);
        setYear(year);
        setMileage(mileage);
        setPlatNumber(platNumber);
    }

    @Override
    public String getCategory() {
        return "KENDARAAN";
    }

    @Override
    public void displayInfo() {
        System.out.println("[Kendaraan] " + title + " | Brand: " + brand + " | Tahun: " + year + " | Kilometer: " + mileage);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getPlatNumber() {
        return platNumber;
    }

    public void setPlatNumber(String platNumber) {
        this.platNumber = platNumber;
    }
}
