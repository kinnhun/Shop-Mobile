/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;

/**
 *
 * @author trung
 */
public class ProductAttributes {
    private int attributeId;
    private Products productId;
    private String color;
    private String storage;
    private String size;
    private BigDecimal extraPrice;
    private int stockQuantity;

    public ProductAttributes() {
    }

    public ProductAttributes(int attributeId, Products productId, String color, String storage, String size, BigDecimal extraPrice, int stockQuantity) {
        this.attributeId = attributeId;
        this.productId = productId;
        this.color = color;
        this.storage = storage;
        this.size = size;
        this.extraPrice = extraPrice;
        this.stockQuantity = stockQuantity;
    }

    public int getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }

    public Products getProductId() {
        return productId;
    }

    public void setProductId(Products productId) {
        this.productId = productId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigDecimal getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(BigDecimal extraPrice) {
        this.extraPrice = extraPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "ProductAttributes{" + "attributeId=" + attributeId + ", productId=" + productId + ", color=" + color + ", storage=" + storage + ", size=" + size + ", extraPrice=" + extraPrice + ", stockQuantity=" + stockQuantity + '}';
    }
    
}
