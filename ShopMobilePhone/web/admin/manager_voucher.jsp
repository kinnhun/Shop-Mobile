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
                                    <h4 class="card-title">Voucher Datatable</h4>
                                    <button type="button" class="btn btn-success mb-3" data-bs-toggle="modal" data-bs-target="#createVoucherModal">
                                        <i class="fas fa-plus"></i> Tạo Voucher
                                    </button>
                                </div>


                                <div class="card-body">
                                    <div class="table-responsive">

                                        <c:import url="../notification.jsp" />

                                        <table id="example3" class="display" style="min-width: 845px">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Mã Voucher</th>
                                                    <th>Phần trăm giảm giá</th>
                                                    <th>Giá trị tối thiểu</th>
                                                    <th>Số lần sử dụng tối đa</th>
                                                    <th>Ngày hết hạn</th>
                                                    <th>Ngày tạo</th>
                                                    <th>Hành động</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="voucher" items="${listVoucher}">
                                                    <tr>
                                                        <td>${voucher.voucherId}</td>
                                                        <td>${voucher.code}</td>
                                                        <td>${voucher.discountPercentage}%</td>
                                                        <td>${voucher.minOrderValue}</td>
                                                        <td>${voucher.maxUsage}</td>
                                                        <td>${voucher.expiryDate}</td>
                                                        <td>${voucher.createdAt}</td>
                                                        <td>
                                                            <div class="d-flex">
                                                                <a href="#" class="btn btn-primary shadow btn-xs sharp me-1" 
                                                                   onclick="openEditModal('${voucher.voucherId}', '${voucher.code}', '${voucher.discountPercentage}', '${voucher.minOrderValue}', '${voucher.maxUsage}', '${voucher.expiryDate}')">
                                                                    <i class="fas fa-pencil-alt"></i>
                                                                </a>
                                                                <a href="#" class="btn btn-danger shadow btn-xs sharp" 
                                                                   onclick="confirmDelete('${voucher.voucherId}')">
                                                                    <i class="fas fa-trash"></i>
                                                                </a>
                                                            </div>												
                                                        </td>
                                                    </tr>
                                                </c:forEach>

                                            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
                                            <script>
                                                                       function confirmDelete(voucherId) {
                                                                           Swal.fire({
                                                                               title: "Bạn có chắc chắn muốn xóa?",
                                                                               text: "Dữ liệu không thể khôi phục sau khi xóa!",
                                                                               icon: "warning",
                                                                               showCancelButton: true,
                                                                               confirmButtonColor: "#d33",
                                                                               cancelButtonColor: "#3085d6",
                                                                               confirmButtonText: "Có, xóa ngay!",
                                                                               cancelButtonText: "Hủy"
                                                                           }).then((result) => {
                                                                               if (result.isConfirmed) {
                                                                                   window.location.href = "manager-voucher?action=delete&voucher_id=" + voucherId;
                                                                               }
                                                                           });
                                                                       }
                                            </script>



                                            </tbody>
                                        </table>

                                        <script>
                                            function openEditModal(voucherId, code, discount, minOrder, maxUsage, expiryDate) {
                                                document.getElementById("editVoucherId").value = voucherId;
                                                document.getElementById("editCode").value = code;
                                                document.getElementById("editDiscount").value = discount;
                                                document.getElementById("editMinOrder").value = minOrder;
                                                document.getElementById("editMaxUsage").value = maxUsage;
                                                document.getElementById("editExpiryDate").value = expiryDate;

                                                var editModal = new bootstrap.Modal(document.getElementById('editVoucherModal'));
                                                editModal.show();
                                            }
                                        </script>

                                        <!-- Modal Chỉnh Sửa Voucher -->
                                        <div class="modal fade" id="editVoucherModal" tabindex="-1" aria-labelledby="editVoucherModalLabel" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="editVoucherModalLabel">Chỉnh sửa thông tin Voucher</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <form id="editVoucherForm" action="manager-voucher" method="post">
                                                            <input name="action" value="edit" type="hidden">
                                                            <input type="text" hidden="" id="editVoucherId" name="voucher_id">
                                                            <div class="mb-3">
                                                                <label for="editCode" class="form-label">Mã Voucher</label>
                                                                <input type="text" class="form-control" id="editCode" name="code" required>
                                                            </div>
                                                            <div class="mb-3">
                                                                <label for="editDiscount" class="form-label">Phần trăm giảm giá</label>
                                                                <input type="number" class="form-control" id="editDiscount" name="discount_percentage" required>
                                                            </div>
                                                            <div class="mb-3">
                                                                <label for="editMinOrder" class="form-label">Giá trị tối thiểu</label>
                                                                <input type="number" class="form-control" id="editMinOrder" name="min_order_value" required>
                                                            </div>
                                                            <div class="mb-3">
                                                                <label for="editMaxUsage" class="form-label">Số lần sử dụng tối đa</label>
                                                                <input type="number" class="form-control" id="editMaxUsage" name="max_usage" required>
                                                            </div>
                                                            <div class="mb-3">
                                                                <label for="editExpiryDate" class="form-label">Ngày hết hạn</label>
                                                                <input type="date" class="form-control" id="editExpiryDate" name="expiry_date" required>
                                                            </div>
                                                            <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>



                                        <!-- Modal Tạo Voucher -->
                                        <div class="modal fade" id="createVoucherModal" tabindex="-1" aria-labelledby="createVoucherModalLabel" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="createVoucherModalLabel">Tạo Voucher Mới</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <form id="createVoucherForm" action="manager-voucher" method="post">
                                                            <input name="action" value="create" type="hidden">
                                                            <div class="mb-3">
                                                                <label for="createCode" class="form-label">Mã Voucher</label>
                                                                <input type="text" class="form-control" id="createCode" name="code" required>
                                                            </div>
                                                            <div class="mb-3">
                                                                <label for="createDiscount" class="form-label">Phần trăm giảm giá</label>
                                                                <input type="number" class="form-control" id="createDiscount" name="discount_percentage" required>
                                                            </div>
                                                            <div class="mb-3">
                                                                <label for="createMinOrder" class="form-label">Giá trị tối thiểu</label>
                                                                <input type="number" class="form-control" id="createMinOrder" name="min_order_value" required>
                                                            </div>
                                                            <div class="mb-3">
                                                                <label for="createMaxUsage" class="form-label">Số lần sử dụng tối đa</label>
                                                                <input type="number" class="form-control" id="createMaxUsage" name="max_usage" required>
                                                            </div>
                                                            <div class="mb-3">
                                                                <label for="createExpiryDate" class="form-label">Ngày hết hạn</label>
                                                                <input type="date" class="form-control" id="createExpiryDate" name="expiry_date" required>
                                                            </div>
                                                            <button type="submit" class="btn btn-primary">Tạo Voucher</button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>




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
