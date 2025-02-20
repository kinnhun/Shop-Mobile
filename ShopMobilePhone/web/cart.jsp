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



        <!-- Breadcrumb Start -->
        <div class="container-fluid">
            <div class="row px-xl-5">
                <div class="col-12">
                    <nav class="breadcrumb bg-light mb-30">
                        <a class="breadcrumb-item text-dark" href="#">Home</a>
                        <a class="breadcrumb-item text-dark" href="#">Shop</a>
                        <span class="breadcrumb-item active">Shopping Cart</span>
                    </nav>
                </div>
            </div>
        </div>
        <!-- Breadcrumb End -->


        <!-- Cart Start -->
        <div class="container-fluid">
            <div class="row px-xl-5">
                <div class="col-lg-8 table-responsive mb-5">
                    <table class="table table-light table-borderless table-hover text-center mb-0">
                        <thead class="thead-dark">
                            <tr>
                                <th>Sản phẩm</th>
                                <th>Dung lượng và màu</th>
                                <th>Giá</th>
                                <th>Số lượng</th>
                                <th>Tổng tiền</th>
                                <th>Remove</th>
                            </tr>
                        </thead>
                        <tbody class="align-middle">



                            <c:forEach var="listCart" items="${listCart}">
                                <tr>
                                    <td class="align-middle"><img src="${listCart.productId.productImage}" alt="" style="width: 50px;"> ${listCart.productId.name}</td>
                                    <td class="align-middle">${listCart.attributeId.storage}; ${listCart.attributeId.color} </td>
                                    <td class="align-middle">${listCart.productId.price + listCart.attributeId.extraPrice} VND</td>
                                    <td class="align-middle">
                                        <form action="cart" method="post" id="cartForm_${listCart.cartId}">
                                            <input name="action" value="quantity" hidden="">
                                            <input name="cartId" value="${listCart.cartId}" hidden="">
                                            <input name="operator" id="operator_${listCart.cartId}" value="" hidden="">
                                            <div class="input-group quantity mx-auto" style="width: 100px;">
                                                <div class="input-group-btn">
                                                    <button type="button" class="btn btn-sm btn-primary btn-minus" onclick="updateQuantity('minus', ${listCart.cartId})">
                                                        <i class="fa fa-minus"></i>
                                                    </button>
                                                </div>
                                                <input type="text" class="form-control form-control-sm bg-secondary border-0 text-center" id="quantityInput_${listCart.cartId}" name="quantity" value="${listCart.quantity}">
                                                <div class="input-group-btn">
                                                    <button type="button" class="btn btn-sm btn-primary btn-plus" onclick="updateQuantity('plus', ${listCart.cartId})">
                                                        <i class="fa fa-plus"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </form>

                                        <script>
                                            function updateQuantity(operator, cartId) {
                                                document.getElementById('operator_' + cartId).value = operator;
                                                document.getElementById('cartForm_' + cartId).submit();
                                            }
                                        </script>
                                    </td>
                                    <td class="align-middle">${(listCart.productId.price + listCart.attributeId.extraPrice) * listCart.quantity} VND</td>

                                    <td class="align-middle">
                                        <form action="cart" method="post" onsubmit="return confirmDelete()">
                                            <input name="action" value="remove" hidden="">
                                            <input name="cartId" value="${listCart.cartId}" hidden="">
                                            <button type="submit" class="btn btn-sm btn-danger">
                                                <i class="fa fa-times"></i>
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        <script>
                            function confirmDelete() {
                                return confirm("Bạn có chắc chắn muốn xóa sản phẩm này khỏi danh sách?");
                            }
                        </script>






                        </tbody>
                    </table>
                </div>
                <div class="col-lg-4">
                    <form class="mb-30" action="cart" method="post">
                        <input name="action" value="voucher" hidden="">
                        <input name="totalCart" value="${totalCart}" hidden="">
                        <div class="input-group">
                            <input name="code" type="text" class="form-control border-0 p-4" placeholder="Mã giảm giá">
                            <div class="input-group-append">
                                <button type="submit" class="btn btn-primary">Áp dụng</button>
                            </div>
                        </div>
                    </form>
                    <h5 class="section-title position-relative text-uppercase mb-3"><span class="bg-secondary pr-3">Cart Summary</span></h5>
                    <div class="bg-light p-30 mb-5">
                        <div class="border-bottom pb-2">
                            <div class="d-flex justify-content-between mb-3">
                                <h6>Tổng tiền:</h6>
                                <h6>${totalCart}</h6>
                            </div>
                            <div class="d-flex justify-content-between">
                                <h6 class="font-weight-medium">Giảm giá</h6>
                                <h6 class="font-weight-medium">
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.appliedVoucher}">
                                            ${sessionScope.appliedVoucher.discountPercentage} %
                                        </c:when>
                                        <c:otherwise>0%</c:otherwise>
                                    </c:choose>
                                </h6>
                            </div>
                        </div>
                        <div class="pt-2">
                            <div class="d-flex justify-content-between mt-2">
                                <h5>Total</h5>
                                <h5>
                                    <c:set var="discount" value="${not empty sessionScope.appliedVoucher ? (totalCart * sessionScope.appliedVoucher.discountPercentage / 100) : 0}" />
                                    ${totalCart - discount}
                                </h5>
                            </div>
                            <a href="checkout">
                                <button type="submit" class="btn btn-block btn-primary font-weight-bold my-3 py-3">Thanh toán</button>

                            </a>

                        </div>

                    </div>
                </div>
            </div>
        </div>
        <!-- Cart End -->


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