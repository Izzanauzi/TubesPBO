package com.bidkita.bidkita_backend.dto.request;

public class CreateAuctionRequestDTO {

    private String category;
    private String title;
    private String description;
    private String condition;
    private String imageBase64;
    private double startingPrice;
    private int duration;
    private Double buyNowPrice;

    // ELEKTRONIK
    private String brand;
    private String type;
    private boolean warrantyStatus;

    // FASHION
    private String size;
    private String material;

    // BUKU
    private String author;
    private String publisher;
    private int publishYear;

    // KENDARAAN
    private int year;
    private int mileage;
    private String platNumber;

    public CreateAuctionRequestDTO() {}

    public String getCategory() { 
        return category; 
    }
    public void setCategory(String category) { 
        this.category = category; 
    }

    public String getTitle() { 
        return title; 
    }
    public void setTitle(String title) { 
        this.title = title; 
    }

    public String getDescription() { 
        return description;
    }
    public void setDescription(String description) { 
        this.description = description;
    }

    public String getCondition() { 
        return condition;
    }
    public void setCondition(String condition) { 
        this.condition = condition;
    }

    public String getImageBase64() { 
        return imageBase64;
    }
    public void setImageBase64(String imageBase64) { 
        this.imageBase64 = imageBase64;
    }

    public double getStartingPrice() { 
        return startingPrice;
    }
    public void setStartingPrice(double startingPrice) { 
        this.startingPrice = startingPrice;
    }

    public int getDuration() { 
        return duration;
    }
    public void setDuration(int duration) { 
        this.duration = duration;
    }

    public Double getBuyNowPrice() { 
        return buyNowPrice;
    }
    public void setBuyNowPrice(Double buyNowPrice) { 
        this.buyNowPrice = buyNowPrice;
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
    public void setWarrantyStatus(boolean warrantyStatus) { 
        this.warrantyStatus = warrantyStatus;
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

    public String getAuthor() { 
        return author;
    }
    public void setAuthor(String author) { 
        this.author = author;
    }

    public String getPublisher() { 
        return publisher;
    }
    public void setPublisher(String publisher) { 
        this.publisher = publisher;
    }

    public int getPublishYear() { 
        return publishYear;
    }
    public void setPublishYear(int publishYear) { 
        this.publishYear = publishYear;
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
