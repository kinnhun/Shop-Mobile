<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
                        <a class="breadcrumb-item text-dark" href="shop">Shop</a>
                        <span class="breadcrumb-item active">Shop Detail</span>
                    </nav>
                </div>
            </div>
        </div>
        <!-- Breadcrumb End -->


        <!-- Shop Detail Start -->
        <div class="container-fluid pb-5">
            <div class="row px-xl-5">
                <div class="col-lg-5 mb-30">
                    <div id="product-carousel" class="carousel slide" data-ride="carousel">
                        <div class="carousel-inner bg-light">
                            <c:choose>
                                <c:when test="${not empty listImageProduct}">
                                    <c:forEach var="listImageProduct" items="${listImageProduct}" varStatus="status">
                                        <div class="carousel-item ${status.first ? 'active' : ''}">
                                            <img class="w-100 h-100" src="${listImageProduct.imageUrl}" alt="Image${listImageProduct.imageId}">
                                        </div>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <div class="carousel-item active">
                                        <img class="w-100 h-100" src="path/to/default-image.jpg" alt="No Image Available">
                                    </div>
                                </c:otherwise>
                            </c:choose>


                        </div>
                        <a class="carousel-control-prev" href="#product-carousel" data-slide="prev">
                            <i class="fa fa-2x fa-angle-left text-dark"></i>
                        </a>
                        <a class="carousel-control-next" href="#product-carousel" data-slide="next">
                            <i class="fa fa-2x fa-angle-right text-dark"></i>
                        </a>
                    </div>
                </div>

                <div class="col-lg-7 h-auto mb-30">
                    <div class="h-100 bg-light p-30">
                        <h3>${product.name}</h3>
                        <div class="d-flex mb-3">
                            <div class="text-primary mr-2">
                                <small class="fas fa-star"></small>
                                <small class="fas fa-star"></small>
                                <small class="fas fa-star"></small>
                                <small class="fas fa-star-half-alt"></small>
                                <small class="far fa-star"></small>
                            </div>
                            <small class="pt-1">(99 Reviews)</small>
                        </div>
                        <h3 class="font-weight-semi-bold mb-4" id="formattedPrice">
                            ${product.price} VND
                        </h3>

                        <script>
                            document.addEventListener("DOMContentLoaded", function () {
                                let priceElement = document.getElementById("formattedPrice");
                                let rawPrice = parseFloat("${product.price}");
                                let extraPrice = parseFloat("${extraPrice}"); // Lấy extraPrice từ server

                                if (!isNaN(extraPrice)) {
                                    rawPrice += extraPrice; // Cộng thêm extraPrice nếu có
                                }

                                if (!isNaN(rawPrice)) {
                                    let formattedPrice = rawPrice.toLocaleString("vi-VN", {style: "currency", currency: "VND"});
                                    priceElement.innerText = formattedPrice;
                                }
                            });
                        </script>








                        <form action="detail" method="get" id="productForm">
                            <input type="hidden" name="id" value="${product.productId}">
                            <!-- Tùy chọn "All" chung -->
                            <div class="d-flex mb-3">
                                <strong class="text-dark mr-3">Lựa chọn:</strong>
                                <div class="custom-control custom-radio custom-control-inline">
                                    <input type="radio" class="custom-control-input" id="all-option" name="all" value="all"
                                           onchange="selectAll()" <c:if test="${empty storage and empty color}">checked</c:if>>
                                           <label class="custom-control-label" for="all-option">All</label>
                                    </div>
                                </div>
                                <!-- Chọn dung lượng -->
                                <div class="d-flex mb-3">
                                    <strong class="text-dark mr-3">Dung lượng:</strong>
                                <c:set var="uniqueStorages" value="" />
                                <c:forEach var="attr" items="${empty listProductAttributesByColor ? listProductAttributes : listProductAttributesByColor}" varStatus="status">
                                    <c:if test="${not fn:contains(uniqueStorages, attr.storage)}">
                                        <c:set var="uniqueStorages" value="${uniqueStorages},${attr.storage}" />
                                        <div class="custom-control custom-radio custom-control-inline">
                                            <input type="radio" class="custom-control-input storage-color" id="size-${status.index}" name="storage"
                                                   value="${attr.storage}" onchange="submitForm()"
                                                   <c:if test="${attr.storage eq storage}">checked</c:if>>
                                            <label class="custom-control-label" for="size-${status.index}">${attr.storage}</label>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>

                            <!-- Chọn màu sắc -->
                            <div class="d-flex mb-4">
                                <strong class="text-dark mr-3">Màu:</strong>
                                <c:forEach var="attr" items="${empty listProductAttributesByStorage ? listProductAttributes : listProductAttributesByStorage}">
                                    <div class="custom-control custom-radio custom-control-inline">
                                        <input type="radio" class="custom-control-input storage-color" id="color-${attr.attributeId}" name="color"
                                               value="${attr.color}" onchange="submitForm()"
                                               <c:if test="${attr.color eq color}">checked</c:if>>
                                        <label class="custom-control-label" for="color-${attr.attributeId}">${attr.color}</label>
                                    </div>
                                </c:forEach>
                            </div>


                        </form>

                        <script>
                            function submitForm() {
                                document.getElementById("productForm").submit();
                            }

                            function selectAll() {
                                let allOption = document.getElementById("all-option");
                                let storageColorOptions = document.querySelectorAll(".storage-color");

                                if (allOption.checked) {
                                    // Bỏ chọn tất cả các radio button khác
                                    storageColorOptions.forEach(option => option.checked = false);

                                    // Submit với giá trị rỗng
                                    let form = document.getElementById("productForm");
                                    let url = new URL(form.action, window.location.origin);
                                    url.searchParams.set("id", "${product.productId}");
                                    url.searchParams.set("storage", "");
                                    url.searchParams.set("color", "");
                                    window.location.href = url.toString();
                                }
                            }
                        </script>











                        <div class="d-flex align-items-center mb-4 pt-2">
                            <div class="input-group quantity mr-3" style="width: 130px;">
                                <div class="input-group-btn">
                                    <button class="btn btn-primary btn-minus">
                                        <i class="fa fa-minus"></i>
                                    </button>
                                </div>
                                <input type="text" class="form-control bg-secondary border-0 text-center" value="1">
                                <div class="input-group-btn">
                                    <button class="btn btn-primary btn-plus">
                                        <i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </div>
                            <button class="btn btn-primary px-3"><i class="fa fa-shopping-cart mr-1"></i> Add To
                                Cart</button>
                        </div>
                        <div class="d-flex pt-2">
                            <strong class="text-dark mr-2">Share on:</strong>
                            <div class="d-inline-flex">
                                <a class="text-dark px-2" href="">
                                    <i class="fab fa-facebook-f"></i>
                                </a>
                                <a class="text-dark px-2" href="">
                                    <i class="fab fa-twitter"></i>
                                </a>
                                <a class="text-dark px-2" href="">
                                    <i class="fab fa-linkedin-in"></i>
                                </a>
                                <a class="text-dark px-2" href="">
                                    <i class="fab fa-pinterest"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row px-xl-5">
                <div class="col">
                    <div class="bg-light p-30">
                        <div class="nav nav-tabs mb-4">
                            <a class="nav-item nav-link text-dark active" data-toggle="tab" href="#tab-pane-1">Mô tả</a>
                            <a class="nav-item nav-link text-dark" data-toggle="tab" href="#tab-pane-3">Dánh giá (0)</a>
                        </div>
                        <div class="tab-content">
                            <div class="tab-pane fade show active" id="tab-pane-1">
                                <h4 class="mb-3">Mô tả sản phẩm</h4>
                                <p class="mb-4">${product.description}</p>
                            </div>

                            <div class="tab-pane fade" id="tab-pane-3">
                                <div class="row">
                                    <div class="col-md-6">
                                        <h4 class="mb-4">1 review for "Product Name"</h4>



                                        <c:forEach var="listReview" items="${listReview}">
                                            <div class="media mb-4">
                                                <img src="${listReview.userId.avatar}" alt="Image" class="img-fluid mr-3 mt-1" style="width: 45px;">
                                                <div class="media-body">
                                                    <h6>${listReview.userId.fullName}<small> - <i>


                                                                <span id="formattedDate">${listReview.createdAt}</span>

                                                                <script>
                                                                    document.addEventListener("DOMContentLoaded", function () {
                                                                        let dateElement = document.getElementById("formattedDate");
                                                                        let rawDate = dateElement.innerText.trim();

                                                                        let parsedDate = new Date(rawDate);
                                                                        if (!isNaN(parsedDate.getTime())) {
                                                                            let formattedDate = parsedDate.toLocaleDateString("en-GB", {
                                                                                day: "2-digit",
                                                                                month: "short",
                                                                                year: "numeric"
                                                                            });

                                                                            dateElement.innerText = formattedDate;
                                                                        }
                                                                    });
                                                                </script>


                                                            </i></small></h6>
                                                    <div class="text-primary mb-2">
                                                        <c:forEach var="i" begin="1" end="${listReview.rating}">
                                                            <i class="fas fa-star"></i> <!-- Sao đầy -->
                                                        </c:forEach>

                                                        <c:if test="${listReview.rating % 1 >= 0.5}">
                                                            <i class="fas fa-star-half-alt"></i> <!-- Sao nửa -->
                                                        </c:if>

                                                        <c:forEach var="i" begin="1" end="${5 - listReview.rating}">
                                                            <i class="far fa-star"></i> <!-- Sao rỗng -->
                                                        </c:forEach>
                                                    </div>

                                                    <p></p>
                                                </div>
                                            </div>
                                        </c:forEach>





                                    </div>
                                    <div class="col-md-6">
                                        <h4 class="mb-4">Leave a review</h4>
                                        <small>Your email address will not be published. Required fields are marked *</small>
                                        <div class="d-flex my-3">
                                            <p class="mb-0 mr-2">Your Rating * :</p>
                                            <div class="text-primary">
                                                <i class="far fa-star"></i>
                                                <i class="far fa-star"></i>
                                                <i class="far fa-star"></i>
                                                <i class="far fa-star"></i>
                                                <i class="far fa-star"></i>
                                            </div>
                                        </div>
                                        <form>
                                            <div class="form-group">
                                                <label for="message">Your Review *</label>
                                                <textarea id="message" cols="30" rows="5" class="form-control"></textarea>
                                            </div>
                                            <div class="form-group">
                                                <label for="name">Your Name *</label>
                                                <input type="text" class="form-control" id="name">
                                            </div>
                                            <div class="form-group">
                                                <label for="email">Your Email *</label>
                                                <input type="email" class="form-control" id="email">
                                            </div>
                                            <div class="form-group mb-0">
                                                <input type="submit" value="Leave Your Review" class="btn btn-primary px-3">
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Shop Detail End -->


        <!-- Products Start -->
        <div class="container-fluid py-5">
            <h2 class="section-title position-relative text-uppercase mx-xl-5 mb-4"><span class="bg-secondary pr-3">You May Also Like</span></h2>
            <div class="row px-xl-5">
                <div class="col">
                    <div class="owl-carousel related-carousel">

                        <c:forEach var="listProduct" items="${listTop8NewProduct}">
                            <div class="product-item bg-light">
                                <div class="product-img position-relative overflow-hidden">
                                    <img class="img-fluid w-100" src="${listProduct.productImage}" alt="${listProduct.name}">
                                    <div class="product-action">
                                        <a class="btn btn-outline-dark btn-square" href=""><i class="fa fa-shopping-cart"></i></a>
                                        <a class="btn btn-outline-dark btn-square" href=""><i class="far fa-heart"></i></a>
                                        <a class="btn btn-outline-dark btn-square" href=""><i class="fa fa-sync-alt"></i></a>
                                        <a class="btn btn-outline-dark btn-square" href=""><i class="fa fa-search"></i></a>
                                    </div>
                                </div>
                                <div class="text-center py-4">
                                    <a class="h6 text-decoration-none text-truncate" href="">${listProduct.name}</a>
                                    <div class="d-flex align-items-center justify-content-center mt-2">
                                        <h5>${listProduct.price} VND</h5><h6 class="text-muted ml-2"><del></del></h6>
                                    </div>

                                </div>
                            </div>
                        </c:forEach>






                    </div>
                </div>
            </div>
        </div>
        <!-- Products End -->


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