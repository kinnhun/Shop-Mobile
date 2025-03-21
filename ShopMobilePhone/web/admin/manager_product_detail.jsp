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
            <c:import url="../notification.jsp" />


            <div class="content-body">
                <div class="container-fluid">
                    <div class="row justify-content-center">
                        <div class="col-lg-10">
                            <div class="container mt-4">
                                <h2 class="mb-4 text-center">Chi Tiết Sản Phẩm</h2>
                                <div class="card shadow">
                                    <div class="card-header bg-primary text-white text-center">
                                        <h4>${product.name}</h4>
                                    </div>
                                    <div class="card-body">
                                        <div class="row">
                                            <!-- Hình ảnh sản phẩm -->
                                            <div class="col-md-5 text-center">
                                                <img src="${product.productImage}" alt="Hình ảnh sản phẩm" class="img-fluid rounded shadow">
                                            </div>

                                            <!-- Thông tin sản phẩm -->
                                            <div class="col-md-7">
                                                <ul class="list-group">
                                                    <li class="list-group-item"><strong>ID:</strong> ${product.productId}</li>
                                                    <li class="list-group-item"><strong>Mô tả:</strong> ${product.description}</li>
                                                    <li class="list-group-item"><strong>Giá:</strong> <span class="text-danger fw-bold">${product.price} VND</span></li>
                                                    <li class="list-group-item"><strong>Số lượng còn:</strong> ${product.stockQuantity}</li>
                                                    <li class="list-group-item"><strong>Trạng thái:</strong> <span class="badge bg-success">${product.status}</span></li>
                                                    <li class="list-group-item"><strong>Ngày tạo:</strong> ${product.createdAt}</li>
                                                    <li class="list-group-item"><strong>Ngày cập nhật:</strong> ${product.updatedAt}</li>
                                                    <li class="list-group-item"><strong>Đã bán:</strong> ${product.soldQuantity}</li>
                                                </ul>

                                                <!-- Nút mở popup sửa sản phẩm -->
                                                <div class="mt-3 text-center">
                                                    <button class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#editProductModal">
                                                        <i class="fas fa-edit"></i> Sửa sản phẩm
                                                    </button>
                                                </div>
                                            </div>

                                            <!-- Modal (Popup) Sửa Sản Phẩm -->
                                            <div class="modal fade" id="editProductModal" tabindex="-1" aria-labelledby="editProductModalLabel" aria-hidden="true">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header bg-primary text-white">
                                                            <h5 class="modal-title" id="editProductModalLabel">Chỉnh sửa sản phẩm</h5>
                                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>

                                                        <div class="modal-body">
                                                            <form action="manager-product" method="post">
                                                                <input type="hidden" name="productId" value="${product.productId}">
                                                                <input name="action" value="edit-product" hidden="">

                                                                <div class="mb-3">
                                                                    <label for="productName" class="form-label">Tên sản phẩm</label>
                                                                    <input type="text" class="form-control" id="productName" name="name" value="${product.name}" required>
                                                                </div>

                                                                <div class="mb-3">
                                                                    <label for="productDescription" class="form-label">Mô tả</label>
                                                                    <textarea class="form-control" id="productDescription" name="description" rows="3" required>${product.description}</textarea>
                                                                </div>

                                                                <div class="mb-3">
                                                                    <label for="productPrice" class="form-label">Giá (VND)</label>
                                                                    <input type="number" class="form-control" id="productPrice" name="price" value="${product.price}" required>
                                                                </div>


                                                                <div class="mb-3">
                                                                    <label for="productStatus" class="form-label">Trạng thái</label>
                                                                    <select class="form-select" id="productStatus" name="status">
                                                                        <option value="active" ${product.status == 'Available' ? 'selected' : ''}>Có sẵn</option>
                                                                        <option value="inActive" ${product.status == 'Out of Stock' ? 'selected' : ''}>Hết hàng</option>
                                                                    </select>
                                                                </div>

                                                                <div class="text-center">
                                                                    <button type="submit" class="btn btn-success"><i class="fas fa-save"></i> Lưu thay đổi</button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <!-- Thuộc tính sản phẩm -->
                                        <div class="mt-4">
                                            <h5>Thuộc tính sản phẩm</h5>
                                            <table class="table table-bordered">
                                                <thead class="table-dark">
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Màu sắc</th>
                                                        <th>Dung lượng</th>
                                                        <th>Giá thêm</th>
                                                        <th>Số lượng</th>
                                                        <th>Hành động</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${attributes}" var="attr">
                                                        <tr>
                                                            <td>${attr.attributeId}</td>
                                                            <td>${attr.color}</td>
                                                            <td>${attr.storage}</td>
                                                            <td>${attr.extraPrice} VND</td>
                                                            <td>${attr.stockQuantity}</td>
                                                            <td>
                                                                <!-- Nút sửa thuộc tính -->
                                                                <button class="btn btn-sm btn-primary edit-btn"
                                                                        data-id="${attr.attributeId}"
                                                                        data-color="${attr.color}"
                                                                        data-storage="${attr.storage}"
                                                                        data-price="${attr.extraPrice}"
                                                                        data-stock="${attr.stockQuantity}">
                                                                    <i class="fas fa-edit"></i> Sửa
                                                                </button>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>

                                            <!-- Modal Chỉnh sửa (Đặt ngoài vòng lặp) -->
                                            <div class="modal fade" id="editAttributeModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="editModalLabel">Chỉnh sửa thuộc tính</h5>
                                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <form action="manager-product" method="post">
                                                                <input hidden="" name="attributeId" id="editAttributeId">
                                                                <input name="action" value="edit-attribute" hidden="">
                                                                <input name="productId" value="${product.productId}" hidden="">
                                                                
                                                                <div class="mb-3">
                                                                    <label for="color" class="form-label">Màu sắc</label>
                                                                    <input type="text" class="form-control" name="color" id="editColor" required>
                                                                </div>

                                                                <div class="mb-3">
                                                                    <label for="storage" class="form-label">Dung lượng</label>
                                                                    <input type="text" class="form-control" name="storage" id="editStorage" required>
                                                                </div>

                                                                <div class="mb-3">
                                                                    <label for="extraPrice" class="form-label">Giá thêm (VND)</label>
                                                                    <input type="number" class="form-control" name="extraPrice" id="editExtraPrice" required>
                                                                </div>

                                                                <div class="mb-3">
                                                                    <label for="stockQuantity" class="form-label">Số lượng</label>
                                                                    <input type="number" class="form-control" name="stockQuantity" id="editStockQuantity" required>
                                                                </div>

                                                                <button type="submit" class="btn btn-success">Lưu thay đổi</button>
                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- JavaScript để điền dữ liệu vào modal -->
                                            <script>
                                                document.addEventListener("DOMContentLoaded", function () {
                                                    document.querySelectorAll(".edit-btn").forEach(button => {
                                                        button.addEventListener("click", function () {
                                                            let attributeId = this.getAttribute("data-id");
                                                            let color = this.getAttribute("data-color");
                                                            let storage = this.getAttribute("data-storage");
                                                            let extraPrice = this.getAttribute("data-price");
                                                            let stockQuantity = this.getAttribute("data-stock");

                                                            document.getElementById("editAttributeId").value = attributeId;
                                                            document.getElementById("editColor").value = color;
                                                            document.getElementById("editStorage").value = storage;
                                                            document.getElementById("editExtraPrice").value = extraPrice;
                                                            document.getElementById("editStockQuantity").value = stockQuantity;

                                                            // Hiển thị modal
                                                            let modal = new bootstrap.Modal(document.getElementById("editAttributeModal"));
                                                            modal.show();
                                                        });
                                                    });
                                                });
                                            </script>

                                            <!-- Nút thêm thuộc tính -->
                                            <div class="text-center mt-3">
                                                <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addAttributeModal">
                                                    <i class="fas fa-plus"></i> Thêm thuộc tính
                                                </button>
                                            </div>

                                        </div>

                                        <!-- Nút quay lại danh sách -->
                                        <div class="text-center mt-3">
                                            <a href="manager-product" class="btn btn-secondary">
                                                <i class="fas fa-arrow-left"></i> Quay lại danh sách
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal Thêm Thuộc Tính -->
            <div class="modal fade" id="addAttributeModal" tabindex="-1" aria-labelledby="addAttributeModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addAttributeModalLabel">Thêm Thuộc Tính Sản Phẩm</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form id="addAttributeForm" action="manager-product" method="POST">
                                <input name="action" value="add-attribute" hidden="">
                                <!-- ID sản phẩm ẩn -->
                                <input type="hidden" name="productId" value="${product.productId}">

                                <div class="mb-3">
                                    <label for="color" class="form-label">Màu sắc:</label>
                                    <input type="text" class="form-control" id="color" name="color" required>
                                </div>

                                <div class="mb-3">
                                    <label for="storage" class="form-label">Dung lượng:</label>
                                    <input type="text" class="form-control" id="storage" name="storage" required>
                                </div>

                                <div class="mb-3">
                                    <label for="extraPrice" class="form-label">Giá thêm (VND):</label>
                                    <input type="number" class="form-control" id="extraPrice" name="extraPrice" required>
                                </div>

                                <div class="mb-3">
                                    <label for="stockQuantity" class="form-label">Số lượng:</label>
                                    <input type="number" class="form-control" id="stockQuantity" name="stockQuantity" required>
                                </div>

                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                    <button type="submit" class="btn btn-primary">Lưu</button>
                                </div>
                            </form>
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
