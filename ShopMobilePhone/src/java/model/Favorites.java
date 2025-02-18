/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;


public class Favorites {
    private int favoriteId;
    private Users userId;
    private Products productId;
    private Date createAt;

    public Favorites() {
    }

    public Favorites(int favoriteId, Users userId, Products productId, Date createAt) {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.productId = productId;
        this.createAt = createAt;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "Favorites{" + "favoriteId=" + favoriteId + ", userId=" + userId + ", productId=" + productId + ", createAt=" + createAt + '}';
    }
    
}
