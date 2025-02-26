<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
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
        <meta property="og:image" content="https:/fillow.dexignlab.com/xhtml/social-image.png">
        <meta name="format-detection" content="telephone=no">

        <!-- PAGE TITLE HERE -->
        <title>Admin Dashboard</title>

        <!-- FAVICONS ICON -->
        <link rel="shortcut icon" type="image/png" href="images/favicon.png">
        <link href="vendor/jquery-nice-select/css/nice-select.css" rel="stylesheet">
        <link href="vendor/owl-carousel/owl.carousel.css" rel="stylesheet">
        <link rel="stylesheet" href="vendor/nouislider/nouislider.min.css">

        <!-- Style css -->
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>
        <!--**********************************
        
        
                   Nav header start
               ***********************************-->




        <div class="nav-header">
            <a href="index.html" class="brand-logo">
                <svg class="logo-abbr" width="55" height="55" viewbox="0 0 55 55" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd" clip-rule="evenodd" d="M27.5 0C12.3122 0 0 12.3122 0 27.5C0 42.6878 12.3122 55 27.5 55C42.6878 55 55 42.6878 55 27.5C55 12.3122 42.6878 0 27.5 0ZM28.0092 46H19L19.0001 34.9784L19 27.5803V24.4779C19 14.3752 24.0922 10 35.3733 10V17.5571C29.8894 17.5571 28.0092 19.4663 28.0092 24.4779V27.5803H36V34.9784H28.0092V46Z" fill="url(#paint0_linear)"></path>
                <defs>
                </defs>
                </svg>
                <div class="brand-title">
                    <h2 class="">MULTI SHOP</h2>
                    <span class="brand-sub-title">PRJ301</span>
                </div>
            </a>
            <div class="nav-control">
                <div class="hamburger">
                    <span class="line"></span><span class="line"></span><span class="line"></span>
                </div>
            </div>
        </div>
        <!--**********************************
            Nav header end
        ***********************************-->



        <!--**********************************
    Header start
***********************************-->

        <!--**********************************
            Header end ti-comment-alt
        ***********************************-->

        <!--**********************************
            Sidebar start
        ***********************************-->
        <div class="dlabnav">
            <div class="dlabnav-scroll">
                <ul class="metismenu" id="menu">
                    <li><a class="has-arrow " href="javascript:void()" aria-expanded="false">
                            <i class="fas fa-home"></i>
                            <span class="nav-text">Dashboard</span>
                        </a>
                        <ul aria-expanded="false">
                            <li><a href="dashboard">Dashboard</a></li>

                        </ul>

                    </li>

                    <li><a class="has-arrow " href="javascript:void()" aria-expanded="false">
                            <i class="fas fa-info-circle"></i>
                            <span class="nav-text">Quản lý tài khoản</span>
                        </a>
                        <ul aria-expanded="false">
                            <li><a href="manager-user">Tài khoản</a></li>

                        </ul>
                    </li>
                    <li><a class="has-arrow " href="javascript:void()" aria-expanded="false">
                            <i class="fas fa-chart-line"></i>
                            <span class="nav-text">Quản lý voucher</span>
                        </a>
                        <ul aria-expanded="false">
                            <li><a href="manager-voucher">Danh sách voucher</a></li>

                        </ul>
                    </li>
                    <li><a class="has-arrow " href="javascript:void()" aria-expanded="false">
                            <i class="fab fa-bootstrap"></i>
                            <span class="nav-text">Quản lý sản phầm</span>
                        </a>
                        <ul aria-expanded="false">
                            <li><a href="manager-product">Danh sách sản phẩm</a></li>


                        </ul>
                    </li>
                    <li><a class="has-arrow " href="javascript:void()" aria-expanded="false">
                            <i class="fas fa-heart"></i>
                            <span class="nav-text">Quản lý đơn hàng</span>
                        </a>
                        <ul aria-expanded="false">
                            <li><a href="manager-order">Danh sách đơn hàng</a></li>

                        </ul>
                    </li>

                </ul>
                <div class="mt-3">
                    <a href="../login-register?action=logout" class="btn btn-danger w-100">
                        <i class="fas fa-sign-out-alt"></i> Đăng xuất
                    </a>
                </div>
            </div>
        </div>


        <!--**********************************
            Sidebar end
        ***********************************-->
    </body>
</html>
