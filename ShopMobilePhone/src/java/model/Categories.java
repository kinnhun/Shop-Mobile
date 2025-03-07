/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author trung
 */
public class Categories {
     private int categoryId;
    private String categoryName;
    private String status;
    private Date createdAt;
    
    private int quanityCategory;

    public Categories() {
    }

    public Categories(int categoryId, String categoryName, String status, Date createdAt) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Categories(int categoryId, String categoryName, String status, Date createdAt, int quanityCategory) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.status = status;
        this.createdAt = createdAt;
        this.quanityCategory = quanityCategory;
    }
    

    public int getQuanityCategory() {
        return quanityCategory;
    }

    public void setQuanityCategory(int quanityCategory) {
        this.quanityCategory = quanityCategory;
    }
    

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Categories{" + "categoryId=" + categoryId + ", categoryName=" + categoryName + ", status=" + status + ", createdAt=" + createdAt + '}';
    }
    
    
    
}
