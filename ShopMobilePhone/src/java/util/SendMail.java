package util;

import DAO.OrdersDAO;
import java.util.List;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import model.OrderDetails;
import model.Orders;

public class SendMail {

    final String username = "@gmail.com";
    final String password = "j";

    public void sendMail(String email, String name, List<OrderDetails> listOrderDetail) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Mobile Store – Xác nhận đơn hàng #" + listOrderDetail.get(0).getOrderId().getOrderId());

            Orders order = listOrderDetail.get(0).getOrderId();
            StringBuilder productsTable = new StringBuilder();
            productsTable.append("<table style='width:100%; border-collapse: collapse;'>")
                    .append("<tr style='background-color: #007bff; color: white; text-align: left;'>")
                    .append("<th style='padding: 10px; border: 1px solid #ddd;'>Sản phẩm</th>")
                    .append("<th style='padding: 10px; border: 1px solid #ddd;'>Màu sắc</th>")
                    .append("<th style='padding: 10px; border: 1px solid #ddd;'>Dung lượng</th>")
                    .append("<th style='padding: 10px; border: 1px solid #ddd;'>Số lượng</th>")
                    .append("<th style='padding: 10px; border: 1px solid #ddd;'>Giá</th>")
                    .append("</tr>");

            for (OrderDetails od : listOrderDetail) {
                productsTable.append("<tr>")
                        .append("<td style='padding: 10px; border: 1px solid #ddd;'><b>")
                        .append(od.getProductId().getName())
                        .append("</b></td>")
                        .append("<td style='padding: 10px; border: 1px solid #ddd;'>")
                        .append(od.getAttributeId().getColor())
                        .append("</td>")
                        .append("<td style='padding: 10px; border: 1px solid #ddd;'>")
                        .append(od.getAttributeId().getStorage())
                        .append("</td>")
                        .append("<td style='padding: 10px; border: 1px solid #ddd;'>")
                        .append(od.getQuantity())
                        .append("</td>")
                        .append("<td style='padding: 10px; border: 1px solid #ddd;'>")
                        .append(od.getPrice()).append(" VNĐ")
                        .append("</td>")
                        .append("</tr>");
            }

            productsTable.append("</table>");

            String emailContent = "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<style>"
                    + "body { font-family: Arial, sans-serif; color: #333; }"
                    + ".container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; }"
                    + ".header { text-align: center; background-color: #007bff; color: white; padding: 15px; font-size: 20px; }"
                    + ".content { padding: 20px; }"
                    + ".footer { text-align: center; font-size: 12px; color: #777; margin-top: 20px; }"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<div class='container'>"
                    + "<div class='header'>"
                    + "<h2>Mobile Store</h2>"
                    + "</div>"
                    + "<div class='content'>"
                    + "<p>Xin chào <b>" + name + "</b>,</p>"
                    + "<p>Cảm ơn bạn đã đặt hàng tại <b>Mobile Store</b>!</p>"
                    + "<p><b>Mã đơn hàng:</b> #" + order.getOrderId() + "</p>"
                    + "<p><b>Địa chỉ giao hàng:</b> " + order.getShippingAddress() + "</p>"
                    + "<p><b>Trạng thái:</b> " + order.getStatus() + "</p>"
                    + "<p><b>Ngày đặt hàng:</b> " + order.getCreatedAt() + "</p>"
                    + "<h3>Chi tiết đơn hàng:</h3>"
                    + productsTable.toString()
                    + "<p><b>Tổng tiền:</b> " + order.getTotalPrice() + " VNĐ</p>"
                    + "<p>Chúng tôi sẽ liên hệ với bạn sớm nhất để xác nhận và giao hàng.</p>"
                    + "<p>Nếu có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi qua:</p>"
                    + "<p>Email: <a href='mailto:hotro@mobilestore.vn'>hotro@mobilestore.vn</a></p>"
                    + "<p>Hotline: <b>0987-654-321</b></p>"
                    + "</div>"
                    + "<div class='footer'>"
                    + "<p>Trân trọng,<br>Đội ngũ Mobile Store</p>"
                    + "</div>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            message.setContent(emailContent, "text/html; charset=UTF-8");
            Transport.send(message);

            System.out.println("Email xác nhận đơn hàng đã gửi thành công!");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi gửi email xác nhận đơn hàng.");
        }
    }

    public static void main(String[] args) {
        SendMail sendMail = new SendMail();
                OrdersDAO odao = new OrdersDAO();

         List<OrderDetails>listOrderDetail = odao.getLatestOrderDetails(1);
        sendMail.sendMail("@gmail.com", "Trần Trung Kiên", listOrderDetail);
    }
}
