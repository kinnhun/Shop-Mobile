/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Users;
import model.Vouchers;

public class VouchersDAO extends DBContext {

    public boolean checkCodeExist(String code) {
        String sql = "SELECT COUNT(*) FROM Vouchers WHERE code = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Vouchers getVoucherByCode(String code) {
        String sql = "SELECT voucher_id, code, discount_percentage, min_order_value, max_usage, expiry_date, created_at FROM Vouchers WHERE code = ? ";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Vouchers(
                            rs.getInt("voucher_id"),
                            rs.getString("code"),
                            rs.getBigDecimal("discount_percentage"),
                            rs.getBigDecimal("min_order_value"),
                            rs.getInt("max_usage"),
                            rs.getDate("expiry_date"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Vouchers> getAllVoucher() {
        List<Vouchers> voucherList = new ArrayList<>();
        String sql = "SELECT voucher_id, code, discount_percentage, min_order_value, max_usage, expiry_date, created_at FROM Vouchers";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vouchers voucher = new Vouchers(
                        rs.getInt("voucher_id"),
                        rs.getString("code"),
                        rs.getBigDecimal("discount_percentage"),
                        rs.getBigDecimal("min_order_value"),
                        rs.getInt("max_usage"),
                        rs.getDate("expiry_date"),
                        rs.getTimestamp("created_at")
                );
                voucherList.add(voucher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucherList;
    }

    public boolean addVoucher(Vouchers voucher) {
        String sql = "INSERT INTO Vouchers (code, discount_percentage, min_order_value, max_usage, expiry_date, created_at) VALUES (?, ?, ?, ?, ?, GETDATE())";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, voucher.getCode());
            ps.setBigDecimal(2, voucher.getDiscountPercentage());
            ps.setBigDecimal(3, voucher.getMinOrderValue());
            ps.setInt(4, voucher.getMaxUsage());
            ps.setDate(5, new java.sql.Date(voucher.getExpiryDate().getTime())); // Chuyển đổi đúng kiểu

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateVoucher(Vouchers voucher) {
        String sql = "UPDATE Vouchers SET code = ?, discount_percentage = ?, min_order_value = ?, max_usage = ?, expiry_date = ? WHERE voucher_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, voucher.getCode());
            ps.setBigDecimal(2, voucher.getDiscountPercentage());
            ps.setBigDecimal(3, voucher.getMinOrderValue());
            ps.setInt(4, voucher.getMaxUsage());
            ps.setDate(5, (Date) voucher.getExpiryDate());
            ps.setInt(6, voucher.getVoucherId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteVoucher(int id) {
        String sql = "DELETE FROM Vouchers WHERE voucher_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
