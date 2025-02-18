/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.util.Date;

public class Vouchers {

    private int voucherId;
    private String code;
    private BigDecimal discountPercentage;
    private BigDecimal minOrderValue;
    private int maxUsage;
    private Date expiryDate;
    private Date createdAt;

    public Vouchers(int voucherId, String code, BigDecimal discountPercentage, BigDecimal minOrderValue, int maxUsage, Date expiryDate, Date createdAt) {
        this.voucherId = voucherId;
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.minOrderValue = minOrderValue;
        this.maxUsage = maxUsage;
        this.expiryDate = expiryDate;
        this.createdAt = createdAt;
    }

    public Vouchers() {
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public BigDecimal getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(BigDecimal minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    public int getMaxUsage() {
        return maxUsage;
    }

    public void setMaxUsage(int maxUsage) {
        this.maxUsage = maxUsage;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Vouchers{" + "voucherId=" + voucherId + ", code=" + code + ", discountPercentage=" + discountPercentage + ", minOrderValue=" + minOrderValue + ", maxUsage=" + maxUsage + ", expiryDate=" + expiryDate + ", createdAt=" + createdAt + '}';
    }
    
    
}
