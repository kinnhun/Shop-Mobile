<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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



        <!-- Checkout Start -->
        <div class="container-fluid">
            <div class="row px-xl-5">

                <div class="col-lg-8">
                    <h5 class="section-title position-relative text-uppercase mb-3">
                        <span class="bg-secondary pr-3">Địa chỉ order</span>
                    </h5>
                    <div class="bg-light p-30 mb-5">
                        <div class="row">
                            <div class="col-md-12 form-group">
                                <label>Địa chỉ chi tiết nhận:</label>
                                <textarea 
                                    class="form-control" 
                                    rows="3" 
                                    placeholder="123 Đường ABC, Phường X, Quận Y"
                                    oninput="document.getElementById('shippingAddressInput').value = this.value"></textarea>
                            </div>
                        </div>
                    </div>
                </div>



                <div class="col-lg-4">
                    <h5 class="section-title position-relative text-uppercase mb-3"><span class="bg-secondary pr-3">Order Total</span></h5>
                    <div class="bg-light p-30 mb-5">
                        <div class="border-bottom">
                            <h6 class="mb-3">Sản phẩm:</h6>

                            <c:forEach items="${listOrder}" var="listOrder">
                                <div class="d-flex justify-content-between">
                                    <p>${listOrder.productId.name} - SL: ${listOrder.quantity}</p>
                                    <p>${listOrder.productId.price + listOrder.attributeId.extraPrice} VND</p>
                                </div>
                            </c:forEach>



                        </div>
                        <div class="border-bottom pt-3 pb-2">
                            <div class="d-flex justify-content-between mb-3">
                                <h6>Tổng tiền:</h6>
                                <h6>${totalCart}</h6>
                            </div>
                            <div class="d-flex justify-content-between">
                                <h6 class="font-weight-medium">Mã giảm giá</h6>
                                <h6 class="font-weight-medium"> <c:choose>
                                        <c:when test="${not empty sessionScope.appliedVoucher}">
                                            ${sessionScope.appliedVoucher.discountPercentage} %
                                        </c:when>
                                        <c:otherwise>0%</c:otherwise>
                                    </c:choose></h6>
                            </div>
                        </div>
                        <div class="pt-2">
                            <div class="d-flex justify-content-between mt-2">
                                <h5>Tổng</h5>
                                <h5> <c:set var="discount" value="${not empty sessionScope.appliedVoucher ? (totalCart * sessionScope.appliedVoucher.discountPercentage / 100) : 0}" />
                                    ${totalCart - discount}</h5>
                            </div>
                        </div>
                        <form action="checkout" method="post">
                            <input hidden name="totalPrice" value="${totalCart - discount}"> 
                            <input hidden="" name="shippingAddress" id="shippingAddressInput" value="">                   
                            <button type="submit" class="btn btn-block btn-primary font-weight-bold py-3">Place Order</button>
                        </form>

                    </div>

                </div>
            </div>
        </div>
        <!-- Checkout End -->


        <!-- Footer Start -->
        <c:import url="./footer.jsp" />

        <!-- Footer End -->


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