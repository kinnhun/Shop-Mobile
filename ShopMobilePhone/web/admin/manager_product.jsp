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
                                    <h4 class="card-title">Product Datatable</h4>
                                    <button type="button" class="btn btn-success mb-3" data-bs-toggle="modal" data-bs-target="#createProductModal">
                                        <i class="fas fa-plus"></i> Tạo Product
                                    </button>
                                </div>


                                <div class="card-body">
                                    <div class="table-responsive">

                                        <c:import url="../notification.jsp" />





                                        <table id="example3" class="display" style="min-width: 845px">
                                            <thead >
                                                <tr>
                                                    <th>Hình ảnh</th>
                                                    <th>Tên sản phẩm</th>
                                                    <th>Mô tả</th>
                                                    <th>Giá</th>
                                                    <th>Số lượng trong kho</th>
                                                    <th>Đã bán</th>
                                                    <th>Trạng thái</th>
                                                    <th>Ngày tạo</th>
                                                    <th>Ngày cập nhật</th>
                                                    <th>Xem Chi tiết</th>
                                                </tr>
                                            </thead>
                                            <tbody class="align-middle">

                                                <c:forEach var="listProduct" items="${listProduct}">
                                                    <tr>
                                                        <!-- Hình ảnh sản phẩm -->
                                                        <td class="align-middle">
                                                            <img src="${listProduct.productImage}" alt="Hình ảnh" style="width: 50px;">
                                                        </td>

                                                        <!-- Tên sản phẩm -->
                                                        <td class="align-middle">${listProduct.name}</td>


                                                        <!-- Mô tả -->
                                                        <td class="align-middle">${listProduct.description}</td>

                                                        <!-- Giá -->
                                                        <td class="align-middle">${listProduct.price} VND</td>

                                                        <!-- Số lượng trong kho -->
                                                        <td class="align-middle">${listProduct.stockQuantity}</td>

                                                        <!-- Số lượng đã bán -->
                                                        <td class="align-middle">${listProduct.soldQuantity}</td>

                                                        <!-- Trạng thái -->
                                                        <td class="align-middle">${listProduct.status}</td>

                                                        <!-- Ngày tạo -->
                                                        <td class="align-middle">${listProduct.createdAt}</td>

                                                        <!-- Ngày cập nhật -->
                                                        <td class="align-middle">${listProduct.updatedAt}</td>
                                                        <td>
                                                            <button class="btn btn-primary" 
                                                                    onclick="window.location.href = 'manager-product?action=detail&id=${listProduct.productId}'">
                                                                <i class="bi bi-eye"></i> Xem chi tiết
                                                            </button>
                                                        </td>


                                                    </tr>
                                                </c:forEach>


                                            </tbody>
                                        </table>

                                        <!-- Xác nhận xóa sản phẩm khỏi danh sách yêu thích -->
                                        <script>
                                            function confirmDelete() {
                                                return confirm("Bạn có chắc chắn muốn xóa sản phẩm này khỏi danh sách yêu thích?");
                                            }
                                        </script>


                                        <div class="modal fade" id="createProductModal" tabindex="-1" aria-labelledby="createProductModalLabel" aria-hidden="true">
                                            <div class="modal-dialog modal-lg">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="createProductModalLabel">Tạo Sản Phẩm Mới</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <form action="manager-product" method="post">
                                                            <input type="hidden" name="action" value="create">

                                                            <div class="row">
                                                                <!-- Cột trái -->
                                                                <div class="col-md-6">
                                                                    <div class="mb-3">
                                                                        <label for="productName" class="form-label">Tên sản phẩm</label>
                                                                        <input type="text" class="form-control" id="productName" name="name" required>
                                                                    </div>

                                                                    <div class="mb-3">
                                                                        <label for="productCategory" class="form-label">Danh mục</label>
                                                                        <select class="form-select" id="productCategory" name="categoryId" required>
                                                                            <option value="">-- Chọn danh mục --</option>
                                                                            <c:forEach var="category" items="${listCategories}">
                                                                                <option value="${category.categoryId}">${category.categoryName}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>

                                                                    <div class="mb-3">
                                                                        <label for="productPrice" class="form-label">Giá (VND)</label>
                                                                        <input type="number" class="form-control" id="productPrice" name="price" required>
                                                                    </div>

                                                                    <div class="mb-3">
                                                                        <label for="productDescription" class="form-label">Mô tả</label>
                                                                        <textarea class="form-control" id="productDescription" name="description" rows="3" required></textarea>
                                                                    </div>
                                                                </div>

                                                                <!-- Cột phải -->
                                                                <div class="col-md-6">
                                                                    <div class="mb-3">
                                                                        <label for="productStatus" class="form-label">Trạng thái</label>
                                                                        <select class="form-select" id="productStatus" name="status" required>
                                                                            <option value="Active">Hoạt động</option>
                                                                            <option value="Inactive">Không hoạt động</option>
                                                                        </select>
                                                                    </div>



                                                                    <!-- Khu vực thêm hình ảnh -->
                                                                    <div class="mb-3">
                                                                        <label class="form-label">Hình ảnh sản phẩm (tối đa 6 ảnh)</label>
                                                                        <div id="imageContainer">
                                                                            <div class="input-group mb-2">
                                                                                <input type="text" class="form-control" name="productImages" placeholder="Nhập link ảnh" required>
                                                                                <button type="button" class="btn btn-danger" onclick="removeImageInput(this)">Xóa</button>
                                                                            </div>
                                                                        </div>
                                                                        <button type="button" class="btn btn-primary btn-sm" id="addImageButton" onclick="addImageInput()">Thêm ảnh</button>
                                                                        <p class="text-danger mt-2" id="imageWarning" style="display: none;">Chỉ được thêm tối đa 6 ảnh!</p>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                                                <button type="submit" class="btn btn-primary">Lưu sản phẩm</button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Script thêm/xóa input link ảnh với giới hạn 6 ảnh -->
                                        <script>
                                            function addImageInput() {
                                                let container = document.getElementById("imageContainer");
                                                let inputs = container.getElementsByTagName("input");

                                                if (inputs.length < 6) {
                                                    let newInput = document.createElement("div");
                                                    newInput.classList.add("input-group", "mb-2");
                                                    newInput.innerHTML = `
                                                        <input type="text" class="form-control" name="productImages" placeholder="Nhập link ảnh" required>
                                                        <button type="button" class="btn btn-danger" onclick="removeImageInput(this)">Xóa</button>
                                                    `;
                                                    container.appendChild(newInput);
                                                }

                                                toggleAddImageButton();
                                            }

                                            function removeImageInput(button) {
                                                button.parentElement.remove();
                                                toggleAddImageButton();
                                            }

                                            function toggleAddImageButton() {
                                                let container = document.getElementById("imageContainer");
                                                let addButton = document.getElementById("addImageButton");
                                                let warningText = document.getElementById("imageWarning");

                                                if (container.getElementsByTagName("input").length >= 6) {
                                                    addButton.disabled = true;
                                                    warningText.style.display = "block";
                                                } else {
                                                    addButton.disabled = false;
                                                    warningText.style.display = "none";
                                                }
                                            }
                                        </script>




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
