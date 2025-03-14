<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>MultiShop - Online Shop Website Template</title>
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
        <c:import url="./header.jsp" />



        <!-- Breadcrumb Start -->
        <div class="container-fluid">
            <div class="row px-xl-5">
                <div class="col-12">
                    <nav class="breadcrumb bg-light mb-30">
                        <a class="breadcrumb-item text-dark" href="home">Home</a>
                        <span class="breadcrumb-item active">Login</span>
                    </nav>
                </div>
            </div>
        </div>
        <!-- Breadcrumb End -->


        <!-- Contact Start -->
        <div class="container-fluid">
            <h2 class="section-title position-relative text-uppercase mx-xl-5 mb-4"><span class="bg-secondary pr-3">Login</span></h2>
            <div class="row px-xl-5">
                <div class="col-lg-7 mb-5">
                    <div class="contact-form bg-light p-30">
                        <div id="loginError" class="text-danger"></div>

                        <form action="login-register" method="post" id="loginForm">
                            <input name="action" value="login" hidden=""> 
                            <div class="control-group">
                                <input type="text" class="form-control" name="username" placeholder="Username" required="required" />
                                <p class="help-block text-danger"></p>
                            </div>
                            <div class="control-group">
                                <input type="password" class="form-control" name="password" placeholder="Password" required="required" />
                                <p class="help-block text-danger"></p>
                            </div>
                            <div>
                                <button class="btn btn-primary py-2 px-4" type="submit">Login</button>
                            </div>
                        </form>
                        <div>
                            <a href="login-register?register=true" class="">You do not have an account</a>

                        </div>
                    </div>

                </div>
                <div class="col-lg-5 mb-5">
                    <div class="bg-light p-30 mb-30">
                        <h3 class="text-uppercase">Giới thiệu về trang web</h3>
                        <p>Chào mừng bạn đến với cửa hàng điện thoại trực tuyến của chúng tôi! Chúng tôi cung cấp những mẫu điện thoại mới nhất từ các thương hiệu hàng đầu như Apple, Samsung, Xiaomi, và nhiều hơn nữa. Với cam kết mang đến sản phẩm chất lượng và dịch vụ tận tâm, chúng tôi giúp bạn dễ dàng tìm kiếm và mua sắm chiếc điện thoại phù hợp nhất với nhu cầu của mình.</p>
                    </div>
                    <div class="bg-light p-30 mb-3"></div>
                </div>
            </div>
        </div>
        <!-- Contact End -->

        <c:import url="./footer.jsp" />




        <!-- Back to Top -->
        <a href="#" class="btn btn-primary back-to-top"><i class="fa fa-angle-double-up"></i></a>


        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>

        <!-- Contact Javascript File -->
        <script src="mail/jqBootstrapValidation.min.js"></script>
        <script src="mail/contact.js"></script>

        <!-- Template Javascript -->
        <script src="js/main.js"></script>
    </body>

</html>