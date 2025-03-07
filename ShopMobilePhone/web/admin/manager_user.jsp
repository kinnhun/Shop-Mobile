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
                                    <h4 class="card-title">Profile Datatable</h4>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">

                                        <c:import url="../notification.jsp" />


                                        <table id="example3" class="display" style="min-width: 845px">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Họ và tên</th>
                                                    <th>Email</th>
                                                    <th>Số điện thoại</th>
                                                    <th>Địa chỉ</th>
                                                    <th>Vai trò</th>
                                                    <th>Trạng thái</th>
                                                    <th>Ngày tạo</th>
                                                    <th>Ngày cập nhật</th>
                                                    <th>Hành động</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="user" items="${listUsers}">

                                                    <tr>
                                                        <td><img class="rounded-circle" width="35" src="${user.avatar}" alt="${user.fullName}"></td>
                                                        <td>${user.fullName}</td>
                                                        <td>${user.email}</td>
                                                        <td>${user.phone}</td>
                                                        <td>${user.address}</td>
                                                        <td>${user.role}</td>
                                                        <td>${user.status}</td>
                                                        <td>${user.createdAt}</td>
                                                        <td>${user.updatedAt}</td>
                                                        <td>
                                                            <div class="d-flex">
                                                                <c:if test="${idAdmin != user.userId}">
                                                                    <a href="#" class="btn btn-primary shadow btn-xs sharp me-1" 
                                                                       onclick="openEditModal('${user.userId}', '${user.fullName}', '${user.email}', '${user.phone}', '${user.address}', '${user.role}', '${user.status}')">
                                                                        <i class="fas fa-pencil-alt"></i>
                                                                    </a>
                                                                </c:if>
                                                            </div>											
                                                        </td>

                                                    </tr>
                                                </c:forEach>



                                            </tbody>
                                        </table>


                                        <script>
                                            function openEditModal(userId, fullName, email, phone, address, role, status) {
                                                document.getElementById("editUserId").value = userId;
                                                document.getElementById("editFullName").value = fullName;
                                                document.getElementById("editEmail").value = email;
                                                document.getElementById("editPhone").value = phone;
                                                document.getElementById("editAddress").value = address;
                                                document.getElementById("editRole").value = role;
                                                document.getElementById("editStatus").value = status;

                                                var editModal = new bootstrap.Modal(document.getElementById('editUserModal'));
                                                editModal.show();
                                            }

                                        </script>

                                        <!-- Modal Chỉnh Sửa Người Dùng -->
                                        <div class="modal fade" id="editUserModal" tabindex="-1" aria-labelledby="editUserModalLabel" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="editUserModalLabel">Chỉnh sửa thông tin người dùng</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <form id="editUserForm" action="manager-user" method="post">
                                                            <input name="action" value="editUser">
                                                            <input type="text" hidden="" id="editUserId" name="user_id">
                                                            <div class="mb-3">
                                                                <label for="editFullName" class="form-label">Họ và tên</label>
                                                                <input type="text" class="form-control" id="editFullName" name="full_name" required>
                                                            </div>
                                                            <div class="mb-3">
                                                                <label for="editEmail" class="form-label">Email</label>
                                                                <input type="email" class="form-control" id="editEmail" name="email">
                                                            </div>
                                                            <div class="mb-3">
                                                                <label for="editPhone" class="form-label">Số điện thoại</label>
                                                                <input type="text" class="form-control" id="editPhone" name="phone" required>
                                                            </div>
                                                            <div class="mb-3">
                                                                <label for="editAddress" class="form-label">Địa chỉ</label>
                                                                <input type="text" class="form-control" id="editAddress" name="address">
                                                            </div>
                                                            <div class="mb-3">
                                                                <label for="editRole" class="form-label">Vai trò</label>
                                                                <select class="form-select" id="editRole" name="role">
                                                                    <option value="customer">Người dùng</option>
                                                                    <option value="admin">Quản trị viên</option>
                                                                </select>
                                                            </div>
                                                            <div class="mb-3">
                                                                <label for="editStatus" class="form-label">Trạng thái</label>
                                                                <select class="form-select" id="editStatus" name="status">
                                                                    <option value="active">Kích hoạt</option>
                                                                    <option value="inactive">Vô hiệu hóa</option>
                                                                </select>
                                                            </div>
                                                            <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
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