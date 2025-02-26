<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="keywords" content="">
        <meta name="author" content="">
        <meta name="robots" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="Fillow : Fillow Saas Admin  Bootstrap 5 Template">
        <meta property="og:title" content="Fillow : Fillow Saas Admin  Bootstrap 5 Template">
        <meta property="og:description" content="Fillow : Fillow Saas Admin  Bootstrap 5 Template">
        <meta property="og:image" content="https://fillow.dexignlab.com/xhtml/social-image.png">
        <meta name="format-detection" content="telephone=no">

        <!-- PAGE TITLE HERE -->
        <title>Admin Dashboard</title>

        <!-- FAVICONS ICON -->
        <link rel="shortcut icon" type="image/png" href="images/favicon.png">
        <!-- Datatable -->
        <link href="vendor/datatables/css/jquery.dataTables.min.css" rel="stylesheet">
        <!-- Custom Stylesheet -->
        <link href="vendor/jquery-nice-select/css/nice-select.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </head>
    <body>

        <!--*******************
            Preloader start
        ********************-->
        <div id="preloader">
            <div class="lds-ripple">
                <div></div>
                <div></div>
            </div>
        </div>
        <!--*******************
            Preloader end
        ********************-->

        <!--**********************************
            Main wrapper start
        ***********************************-->
        <div id="main-wrapper">

            <c:import url="./header.jsp" />




            <!--**********************************
        Content body start
    ***********************************-->
            <div class="content-body">
                <div class="container-fluid">
                    <div class="row">

                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title">Chi tiết đơn hàng</h4>

                                </div>
                                <c:import url="../notification.jsp" />



                                <div class="col-md-7">
                                    <ul class="list-group">
                                        <li class="list-group-item"><strong>ID:</strong> ${order.orderId}</li>
                                        <li class="list-group-item"><strong>Khách hàng:</strong> ${order.userId.phone} - ${order.userId.fullName}</li>
                                        <li class="list-group-item"><strong>Giá trị đơn hàng:</strong> <span class="text-danger fw-bold">${order.totalPrice} VND</span></li>
                                        <li class="list-group-item"><strong>Trạng thái đơn hàng:</strong> 
                                            <span class="badge
                                                  <c:choose>
                                                      <c:when test="${order.status == 'pending'}">bg-warning</c:when>
                                                      <c:when test="${order.status == 'shipped'}">bg-success</c:when>
                                                      <c:when test="${order.status == 'delivered'}">bg-danger</c:when>
                                                      <c:otherwise>bg-secondary</c:otherwise>
                                                  </c:choose>
                                                  ">${order.status}</span>
                                        </li>
                                        <li class="list-group-item"><strong>Địa chỉ giao hàng:</strong> ${order.shippingAddress}</li>
                                        <li class="list-group-item"><strong>Ngày tạo:</strong> ${order.createdAt}</li>
                                        <li class="list-group-item"><strong>Ngày cập nhật:</strong> ${order.updatedAt}</li>
                                    </ul>

                                    <!-- Nút mở popup sửa trạng thái đơn hàng -->
                                    <c:if test="${order.status != 'cancelled' and order.status != 'delivered'}">
                                        <div class="mt-3 text-center">
                                            <button class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#updateOrderStatusModal">
                                                <i class="fas fa-edit"></i> Cập nhật trạng thái đơn hàng
                                            </button>
                                        </div>
                                    </c:if>

                                </div>




                                <!-- Modal cập nhật trạng thái đơn hàng -->
                                <div class="modal fade" id="updateOrderStatusModal" tabindex="-1" aria-labelledby="updateOrderStatusModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="updateOrderStatusModalLabel">Cập nhật trạng thái đơn hàng</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form id="updateOrderStatusForm" action="manager-order" method="POST">
                                                    <input type="hidden" name="orderId" value="${order.orderId}" />

                                                    <div class="mb-3">
                                                        <label for="orderStatus" class="form-label">Chọn trạng thái mới:</label>
                                                        <select class="form-select" name="status" id="orderStatus" required>
                                                            <option value="pending" ${order.status == 'Pending' ? 'selected' : ''}>Chờ xác nhận</option>
                                                            <option value="processing" ${order.status == 'Processing' ? 'selected' : ''}>Đang xử lý</option>
                                                            <option value="shipped" ${order.status == 'Shipped' ? 'selected' : ''}>Đang vận chuyển</option>
                                                            <option value="delivered" ${order.status == 'Delivered' ? 'selected' : ''}>Đã giao hàng</option>
                                                            <option value="cancelled" ${order.status == 'Cancelled' ? 'selected' : ''}>Đã hủy</option>
                                                        </select>

                                                    </div>

                                                    <div class="text-end">
                                                        <button type="submit" class="btn btn-primary">Cập nhật</button>
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>




                                <div class="card-body">
                                    <div class="table-responsive">

                                        <c:import url="../notification.jsp" />

                                        <table id="example3" class="display" style="min-width: 845px">
                                            <thead>
                                                <tr>
                                                    <th>ID Chi tiết</th>
                                                    <th>Sản phẩm</th>
                                                    <th>Thuộc tính</th>
                                                    <th>Số lượng</th>
                                                    <th>Giá</th>
                                                    <th>Tổng giá</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="listOrderDetail" items="${listOrderDetail}">
                                                    <tr>
                                                        <td >${listOrderDetail.orderDetaiId}</td>
                                                        <td >${listOrderDetail.productId.name}</td>
                                                        <td >${listOrderDetail.attributeId.storage} -- ${listOrderDetail.attributeId.color}</td>
                                                        <td>${listOrderDetail.quantity}</td>
                                                        <td >${listOrderDetail.price}</td>
                                                        <td >${listOrderDetail.quantity * orderDetail.price} VND</td>

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

            <!--**********************************
                Content body end
            ***********************************-->




            <!--**********************************
                Footer start
            ***********************************-->
            <div class="footer">
                <div class="copyright">
                    <p>Copyright © Designed &amp; Developed by <a href="../index.htm" target="_blank">DexignLab</a> 2021</p>
                </div>
            </div>
            <!--**********************************
                Footer end
            ***********************************-->

            <!--**********************************
       Support ticket button start
    ***********************************-->

            <!--**********************************
               Support ticket button end
            ***********************************-->


        </div>
        <!--**********************************
            Main wrapper end
        ***********************************-->

        <!--**********************************
            Scripts
        ***********************************-->
        <!-- Required vendors -->
        <script src="vendor/global/global.min.js"></script>
        <script src="vendor/chart.js/Chart.bundle.min.js"></script>
        <!-- Apex Chart -->
        <script src="vendor/apexchart/apexchart.js"></script>

        <!-- Datatable -->
        <script src="vendor/datatables/js/jquery.dataTables.min.js"></script>
        <script src="js/plugins-init/datatables.init.js"></script>

        <script src="vendor/jquery-nice-select/js/jquery.nice-select.min.js"></script>

        <script src="js/custom.min.js"></script>
        <script src="js/dlabnav-init.js"></script>
        <script src="js/demo.js"></script>
        <script src="js/styleSwitcher.js"></script>



    </body>
</html>