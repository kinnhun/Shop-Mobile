/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

import java.math.BigDecimal;


public class OrderDetails {

    private int orderDetaiId;
    private Orders orderId;
    private Products productId;
    private ProductAttributes attributeId;
    private int quantity;
    private BigDecimal price;

    public OrderDetails() {
    }

    public OrderDetails(int orderDetaiId, Orders orderId, Products productId, ProductAttributes attributeId, int quantity, BigDecimal price) {
        this.orderDetaiId = orderDetaiId;
        this.orderId = orderId;
        this.productId = productId;
        this.attributeId = attributeId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderDetaiId() {
        return orderDetaiId;
    }

    public void setOrderDetaiId(int orderDetaiId) {
        this.orderDetaiId = orderDetaiId;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    public Products getProductId() {
        return productId;
    }

    public void setProductId(Products productId) {
        this.productId = productId;
    }

    public ProductAttributes getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(ProductAttributes attributeId) {
        this.attributeId = attributeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderDetails{" + "orderDetaiId=" + orderDetaiId + ", orderId=" + orderId + ", productId=" + productId + ", attributeId=" + attributeId + ", quantity=" + quantity + ", price=" + price + '}';
    }
    
    
}
