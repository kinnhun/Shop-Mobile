<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Voucher Management</title>

        <!-- FAVICONS ICON -->
        <link rel="shortcut icon" type="image/png" href="images/favicon.png">
        <!-- Datatable -->
        <link href="vendor/datatables/css/jquery.dataTables.min.css" rel="stylesheet">
        <!-- Custom Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </head>
    <body>

        <div id="main-wrapper">
            <c:import url="./header.jsp" />

            <div class="content-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Quản lý order</h4>

                                </div>


                                <div class="card-body">
                                    <div class="table-responsive">

                                        <c:import url="../notification.jsp" />

                                        <table id="example3" class="display" style="min-width: 845px">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Người dùng</th>
                                                    <th>Giá</th>
                                                    <th>Trạng thái</th>
                                                    <th>Địa chỉ ship hàng</th>
                                                    <th>Ngày tạo</th>
                                                    <th>Ngày cập nhật</th>
                                                    <th>Hành động</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="listOrder" items="${listOrder}">
                                                    <tr>
                                                        <td>${listOrder.orderId}</td>
                                                        <td>${listOrder.userId.phone} - ${listOrder.userId.fullName}</td>
                                                        <td>${listOrder.totalPrice} VND</td>
                                                        <td>${listOrder.status}</td>
                                                        <td>${listOrder.shippingAddress}</td>
                                                        <td>${listOrder.createdAt}</td>
                                                        <td>${listOrder.updatedAt}</td>
                                                        <td>
                                                            <button class="btn btn-primary" 
                                                                    onclick="window.location.href = 'manager-order?action=detail&id=${listOrder.orderId}'">
                                                                <i class="bi bi-eye"></i> Xem chi tiết
                                                            </button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>





                                            </tbody>
                                        </table>







                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="footer">
                <div class="copyright">
                    <p>Copyright © Designed &amp; Developed by Vecopharma 2025</p>
                </div>
            </div>

        </div>

        <!-- Required vendors -->
        <script src="vendor/global/global.min.js"></script>
        <script src="vendor/chart.js/Chart.bundle.min.js"></script>
        <script src="vendor/datatables/js/jquery.dataTables.min.js"></script>
        <script src="js/plugins-init/datatables.init.js"></script>
        <script src="js/custom.min.js"></script>
        <script src="js/dlabnav-init.js"></script>

    </body>
</html>
