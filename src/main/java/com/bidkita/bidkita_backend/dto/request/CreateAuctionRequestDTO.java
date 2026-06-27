package com.bidkita.bidkita_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAuctionRequestDTO {

    private String category;
    private String title;
    private String description;
    private String condition;
    private String imageBase64;

    private Double startingPrice;
    private Integer duration;
    private Double buyNowPrice;

    // Elektronik
    private String brand;
    private String type;
    private Boolean warrantyStatus;

    // Fashion
    private String size;
    private String material;

    // Buku
    private String author;
    private String publisher;
    private Integer publishYear;

    // Kendaraan
    private Integer year;
    private Integer mileage;
    private String platNumber;
}