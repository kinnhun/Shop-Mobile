/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
   
    public static void main(String[] args) {
        VouchersDAO vdao = new VouchersDAO();
        Vouchers vv = vdao.getVoucherByCode("FREESHIP");
        System.out.println(vv.toString());
    }
}
