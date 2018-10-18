package com.beehyv.confused1.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;
    private String productName;
    private Double productPrice;
    private boolean available = true;
    private byte[] ProductImage;

    private String category = Category.ANONYMOUS.name();


    public Product(String productName, Double price) {
        this.productName = productName;
        this.productPrice = price;
    }

    public Product() {
    }

    public Integer getPrductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return productPrice;
    }

    public void setPrice(Double price) {
        this.productPrice = price;
    }

    public byte[] getProductImage() {
        return ProductImage;
    }

    public void setProductImage(byte[] productImage) {
        ProductImage = productImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + productPrice +
                ", ProductImage=" + Arrays.toString(ProductImage) +
                ", category='" + category + '\'' +
                '}';
    }
}
