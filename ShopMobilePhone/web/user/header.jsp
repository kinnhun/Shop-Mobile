<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>MultiShop - Online Shop Website</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="Free HTML Templates" name="keywords">
        <meta content="Free HTML Templates" name="description">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">  

        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/animate/animate.min.css" rel="stylesheet">
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>
        <!-- Topbar Start -->
        <div class="container-fluid">
            <div class="row bg-secondary py-1 px-xl-5">
                <div class="col-lg-6 d-none d-lg-block">
                    <div class="d-inline-flex align-items-center h-100">
                        <a class="text-body mr-3" href="">About</a>
                        <a class="text-body mr-3" href="">Contact</a>
                    </div>
                </div>
                <div class="col-lg-6 text-center text-lg-right">
                    <div class="d-inline-flex align-items-center">
                        <div class="btn-group">
                            <button type="button" class="btn btn-sm btn-light dropdown-toggle" data-toggle="dropdown">
                                Tài khoản
                            </button>
                            <div class="dropdown-menu dropdown-menu-right">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.username}">
                                        <a href="profile.jsp" class="dropdown-item">Trang cá nhân</a>
                                        <a href="login-register?action=logout" class="dropdown-item">Đăng xuất</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="login-register" class="dropdown-item">Đăng ký - Đăng nhập</a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>



                    </div>
                    <div class="d-inline-flex align-items-center d-block d-lg-none">
                        <a href="favorites" class="btn px-0 ml-2">
                            <i class="fas fa-heart text-dark"></i>
                            <span class="badge text-dark border border-dark rounded-circle" style="padding-bottom: 2px;">${sessionScope.countFavorite}</span>
                        </a>
                        <a href="cart" class="btn px-0 ml-2">
                            <i class="fas fa-shopping-cart text-dark"></i>
                            <span class="badge text-dark border border-dark rounded-circle" style="padding-bottom: 2px;">${sessionScope.countCart}</span>
                        </a>
                    </div>
                </div>
            </div>
            <div class="row align-items-center bg-light py-3 px-xl-5 d-none d-lg-flex">
                <div class="col-lg-4">
                    <a href="home" class="text-decoration-none">
                        <span class="h1 text-uppercase text-primary bg-dark px-2">Multi</span>
                        <span class="h1 text-uppercase text-dark bg-primary px-2 ml-n1">Shop</span>
                    </a>
                </div>
                <div class="col-lg-4 col-6 text-left">



                    <!-- Form tìm kiếm -->
                   <form action="shop" method="get" class="position-relative" id="searchForm">
    <input name="action" value="search" hidden="">
    <div class="input-group">
        <input name="searchText" type="text" id="searchInput" class="form-control" placeholder="Search for products" autocomplete="off">
        <div class="input-group-append">
            <span class="input-group-text bg-transparent text-primary" id="searchIcon">
                <i class="fa fa-search"></i>
            </span>
        </div>
    </div>
    <ul id="searchResults" class="list-group position-absolute w-100 mt-1 d-none" style="z-index: 1000;"></ul>
</form>

<script>
    document.getElementById("searchIcon").addEventListener("click", function() {
        document.getElementById("searchForm").submit();
    });
</script>



                    <!-- Danh sách sản phẩm dưới dạng JavaScript -->
                    <script>
                        // Lấy danh sách sản phẩm từ JSP
                        const allProducts = [
                        <c:forEach var="product" items="${allProducts}">
                            {id: "${product.productId}", name: "${product.name}"},
                        </c:forEach>
                        ];

                        // Lấy phần tử input và danh sách gợi ý
                        const searchInput = document.getElementById("searchInput");
                        const searchResults = document.getElementById("searchResults");

                        // Xử lý nhập liệu & hiển thị gợi ý
                        searchInput.addEventListener("input", function () {
                            const keyword = searchInput.value.toLowerCase();
                            searchResults.innerHTML = ""; // Xóa kết quả cũ

                            if (keyword.length > 0) {
                                const filteredProducts = allProducts.filter(product =>
                                    product.name.toLowerCase().includes(keyword)
                                );

                                if (filteredProducts.length > 0) {
                                    searchResults.classList.remove("d-none"); // Hiện danh sách

                                    filteredProducts.forEach((product, index) => {
                                        const li = document.createElement("li");
                                        li.classList.add("list-group-item", "list-group-item-action");
                                        li.textContent = product.name;
                                        li.setAttribute("data-id", product.id);
                                        li.setAttribute("tabindex", index); // Cho phép dùng bàn phím chọn
                                        searchResults.appendChild(li);

                                        // Chọn sản phẩm khi click
                                        li.addEventListener("click", function () {
                                            searchInput.value = product.name;
                                            searchResults.classList.add("d-none"); // Ẩn dropdown
                                        });

                                        // Chọn bằng bàn phím
                                        li.addEventListener("keydown", function (event) {
                                            if (event.key === "Enter") {
                                                searchInput.value = product.name;
                                                searchResults.classList.add("d-none"); // Ẩn dropdown
                                                event.preventDefault(); // Ngăn form submit
                                            }
                                        });
                                    });
                                } else {
                                    searchResults.classList.add("d-none"); // Ẩn dropdown nếu không có kết quả
                                }
                            } else {
                                searchResults.classList.add("d-none"); // Ẩn nếu input rỗng
                            }
                        });

                        // Ẩn dropdown khi click ra ngoài
                        document.addEventListener("click", function (event) {
                            if (!searchInput.contains(event.target) && !searchResults.contains(event.target)) {
                                searchResults.classList.add("d-none");
                            }
                        });

                    </script>






                </div>
                <div class="col-lg-4 col-6 text-right">
                    <p class="m-0">FPT</p>
                    <h5 class="m-0">PRJ301</h5>
                </div>
            </div>
        </div>
        <!-- Topbar End -->


        <!-- Navbar Start -->
        <div class="container-fluid bg-dark mb-30">
            <div class="row px-xl-5">
                <div class="col-lg-3 d-none d-lg-block">
                    <a class="btn d-flex align-items-center justify-content-between bg-primary w-100" data-toggle="collapse" href="#navbar-vertical" style="height: 65px; padding: 0 30px;">
                        <h6 class="text-dark m-0"><i class="fa fa-bars mr-2"></i>Categories</h6>
                        <i class="fa fa-angle-down text-dark"></i>
                    </a>
                    <nav class="collapse position-absolute navbar navbar-vertical navbar-light align-items-start p-0 bg-light" id="navbar-vertical" style="width: calc(100% - 30px); z-index: 999;">
                        <div class="navbar-nav w-100">
                            <c:forEach var="category" items="${listCategories}">
                                <a href="shop?categoryId=${category.categoryId}" class="nav-item nav-link">${category.categoryName}</a>
                            </c:forEach>
                        </div>

                    </nav>
                </div>
                <div class="col-lg-9">
                    <nav class="navbar navbar-expand-lg bg-dark navbar-dark py-3 py-lg-0 px-0">
                        <a href="" class="text-decoration-none d-block d-lg-none">
                            <span class="h1 text-uppercase text-dark bg-light px-2">Multi</span>
                            <span class="h1 text-uppercase text-light bg-primary px-2 ml-n1">Shop</span>
                        </a>
                        <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse justify-content-between" id="navbarCollapse">
                            <div class="navbar-nav mr-auto py-0">
                                <a href="home" class="nav-item nav-link active">Trang chủ</a>
                                <a href="shop" class="nav-item nav-link">Shop</a>
                                <div class="nav-item dropdown">
                                    <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">Trang <i class="fa fa-angle-down mt-1"></i></a>
                                    <div class="dropdown-menu bg-primary rounded-0 border-0 m-0">
                                        <a href="cart" class="dropdown-item">Giỏ hàng</a>
                                        <a href="favorites" class="dropdown-item">Yêu thích</a>
                                        <a href="order" class="dropdown-item">Order</a>
                                    </div>
                                </div>
                                <a href="contact.html" class="nav-item nav-link">Contact</a>
                            </div>
                            <div class="navbar-nav ml-auto py-0 d-none d-lg-block">
                                <a href="favorites" class="btn px-0">
                                    <i class="fas fa-heart text-primary"></i>
                                    <span class="badge text-secondary border border-secondary rounded-circle" style="padding-bottom: 2px;">${sessionScope.countFavorite}</span>
                                </a>
                                <a href="cart" class="btn px-0 ml-3">
                                    <i class="fas fa-shopping-cart text-primary"></i>
                                    <span class="badge text-secondary border border-secondary rounded-circle" style="padding-bottom: 2px;">${sessionScope.countCart}</span>
                                </a>
                            </div>
                        </div>
                    </nav>
                </div>
            </div>
        </div>
        <%@ include file="../notification.jsp" %>

        <!-- Navbar End -->
    </body>
</html>
