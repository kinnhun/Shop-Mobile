/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

public class Cart {

    private int cartId;
    private Users userId;
    private Products productId;
    private ProductAttributes attributeId;
    private int quantity;
    private Date createdAt;

    public Cart(int cartId, Users userId, Products productId, ProductAttributes attributeId, int quantity, Date createdAt) {
        this.cartId = cartId;
        this.userId = userId;
        this.productId = productId;
        this.attributeId = attributeId;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public Cart() {
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Cart{" + "cartId=" + cartId + ", userId=" + userId + ", productId=" + productId + ", attributeId=" + attributeId + ", quantity=" + quantity + ", createdAt=" + createdAt + '}';
    }

}
