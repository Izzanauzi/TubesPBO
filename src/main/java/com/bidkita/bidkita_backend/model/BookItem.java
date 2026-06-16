package com.bidkita.bidkita_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "book_items")
@PrimaryKeyJoinColumn(name = "item_id")
public class BookItem extends Item {

    private String author;
    private String publisher;
    private int publishYear;

    public BookItem() {}

    public BookItem(String title, String description, String condition, String imageBase64,
                    double startingPrice, String author, String publisher, int publishYear) {
        setTitle(title);
        setDescription(description);
        setCondition(condition);
        setImageBase64(imageBase64);
        setStartingPrice(startingPrice);
        setAuthor(author);
        setPublisher(publisher);
        setPublishYear(publishYear);
    }

    @Override
    public String getCategory() {
        return "BUKU";
    }

    @Override
    public void displayInfo() {
        System.out.println("[Buku] " + title + " | Author: " + author + " | Publisher: " + publisher + " | Tahun: " + publishYear);
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
}
