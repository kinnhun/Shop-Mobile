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
                        <a class="breadcrumb-item text-dark" href="#">Shop</a>
                        <span class="breadcrumb-item active">Shop List</span>
                    </nav>
                </div>
            </div>
        </div>
        <!-- Breadcrumb End -->


        <!-- Shop Start -->
        <div class="container-fluid">
            <div class="row px-xl-5">
                <!-- Shop Sidebar Start -->
                <div class="col-lg-3 col-md-4">
                    <!-- Price Start -->
                    <h5 class="section-title position-relative text-uppercase mb-3"><span class="bg-secondary pr-3">Lọc theo thể loại</span></h5>
                    <div class="bg-light p-4 mb-30">




                        <div class="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                            <input type="checkbox" class="custom-control-input" id="category-all" name="categoryId" value=""
                                   onchange="window.location.href = 'shop'"
                                   <c:if test="${requestScope.categoryId == 0}">checked</c:if>>
                            <label class="custom-control-label" for="category-all">Tất cả</label>
                        </div>


                        <form action="shop" method="get">

                            <input hidden="" name="min" value="${requestScope.min}">
                            <input hidden="" name="max" value="${requestScope.max}" >



                            <c:forEach var="category" items="${listCategories}">
                                <div class="custom-control custom-radio d-flex align-items-center justify-content-between">
                                    <input type="radio" class="custom-control-input" id="category-${category.categoryId}" 
                                           name="categoryId" value="${category.categoryId}"
                                           ${requestScope.categoryId == category.categoryId ? 'checked' : ''}
                                           onchange="this.form.submit()">
                                    <label class="custom-control-label" for="category-${category.categoryId}">${category.categoryName}</label>
                                </div>
                            </c:forEach>
                        </form>



                    </div>
                    <!-- Price End -->

                    <!-- Bộ lọc giá với 1 thanh trượt đôi -->
                    <h5 class="section-title position-relative text-uppercase mb-3">
                        <span class="bg-secondary pr-3">Lọc theo giá</span>
                    </h5>
                    <div class="bg-light p-4 mb-30">
                        <form action="shop" method="GET">




                            <input name="categoryId" value="${requestScope.categoryId}" hidden="">
                            <div class="d-flex align-items-center justify-content-between mb-2">
                                <span>Giá từ: <span id="minPriceValue">${minPrice}</span> VND</span>
                                <span>đến: <span id="maxPriceValue">${maxPrice}</span> VND</span>
                            </div>
                            <div class="range-slider">
                                <input type="range" id="minPrice" name="min" min="0" max="${maxPrice}" step="500000" value="${requestScope.min}" oninput="updatePriceValue()">
                                <input type="range" id="maxPrice" name="max" min="0" max="${maxPrice}" step="500000" value="${requestScope.max}" oninput="updatePriceValue()">
                                <div class="slider-track"></div>
                            </div>
                            <button type="submit" class="btn btn-primary mt-3 w-100">Lọc giá</button>
                        </form>
                    </div>
                    <style>
                        /* Định dạng thanh trượt */
                        .range-slider {
                            position: relative;
                            width: 100%;
                            height: 5px;
                            background: #ddd;
                            border-radius: 5px;
                            margin-top: 10px;
                        }

                        .range-slider input {
                            position: absolute;
                            width: 100%;
                            appearance: none;
                            background: none;
                            pointer-events: none;
                        }

                        .range-slider input::-webkit-slider-thumb {
                            appearance: none;
                            width: 16px;
                            height: 16px;
                            background: #727CF5;
                            border-radius: 50%;
                            cursor: pointer;
                            pointer-events: auto;
                        }

                        .slider-track {
                            position: absolute;
                            height: 5px;
                            background: #727CF5;
                            border-radius: 5px;
                            z-index: -1;
                        }

                        .btn {
                            background-color: #727CF5;
                            border: none;
                        }

                    </style>    


                    <script>
                        const minPriceInput = document.getElementById('minPrice');
                        const maxPriceInput = document.getElementById('maxPrice');
                        const minPriceValue = document.getElementById('minPriceValue');
                        const maxPriceValue = document.getElementById('maxPriceValue');

                        function updatePriceValue() {
                            if (parseInt(minPriceInput.value) > parseInt(maxPriceInput.value)) {
                                maxPriceInput.value = minPriceInput.value;
                            }

                            minPriceValue.textContent = minPriceInput.value.toLocaleString() + " VND";
                            maxPriceValue.textContent = maxPriceInput.value.toLocaleString() + " VND";

                            const minPricePosition = (minPriceInput.value / minPriceInput.max) * 100;
                            const maxPricePosition = (maxPriceInput.value / maxPriceInput.max) * 100;

                            document.querySelector('.slider-track').style.left = `${minPricePosition}%`;
                            document.querySelector('.slider-track').style.right = `${100 - maxPricePosition}%`;
                        }

                        minPriceInput.addEventListener('input', updatePriceValue);
                        maxPriceInput.addEventListener('input', updatePriceValue);

                        updatePriceValue();
                    </script>


                    <!-- Color End -->


                </div>
                <!-- Shop Sidebar End -->


                <!-- Shop Product Start -->
                <div class="col-lg-9 col-md-8">
                    <!-- Bộ lọc sắp xếp -->
                    <div class="col-lg-3 col-md-4 mb-4">
                        <div class="filter-section">
                            <h5 class="section-title position-relative text-uppercase mb-3">
                                <span class="bg-secondary pr-3">Sắp xếp theo giá</span>
                            </h5>
                            <form action="shop" method="get">
                                <input hidden="" name="min" value="${requestScope.min}">
                                <input hidden="" name="max" value="${requestScope.max}" ><!-- comment -->
                                <input name="categoryId" value="${requestScope.categoryId}" hidden="">

                                <select name="sortPrice" class="form-control" onchange="this.form.submit()">
                                    <option value="asc" ${param.sortPrice == 'asc' ? 'selected' : ''}>Giá từ thấp đến cao</option>
                                    <option value="desc" ${param.sortPrice == 'desc' ? 'selected' : ''}>Giá từ cao đến thấp</option>
                                </select>
                            </form>



                        </div>
                    </div>


                    <div class="row pb-3">

                        <c:if test="${empty listProduct}">
                            <h1>Không có sản phẩm nào.</h1>
                        </c:if>

                        <c:forEach var="listProduct" items="${listProduct}">

                            <div class="col-lg-4 col-md-6 col-sm-6 pb-1">
                                <div class="product-item bg-light mb-4">
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
                                        <a class="h6 text-decoration-none text-truncate" href="detail?id=${listProduct.productId}">${listProduct.name}</a>
                                        <div class="d-flex align-items-center justify-content-center mt-2">
                                            <h5 class="product-price1">${listProduct.price} VND</h5>
                                            <h6 class="text-muted ml-2"></h6>
                                        </div>
                                        <div class="d-flex align-items-center justify-content-center mb-1">
                                            <small class="fa fa-star text-primary mr-1"></small>
                                            <small class="fa fa-star text-primary mr-1"></small>
                                            <small class="fa fa-star text-primary mr-1"></small>
                                            <small class="fa fa-star text-primary mr-1"></small>
                                            <small class="fa fa-star text-primary mr-1"></small>
                                            <small>(99)</small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>


                        <div class="col-12">
                            <nav>
                                <ul class="pagination justify-content-center" id="pagination">
                                    <li class="page-item disabled" id="prevPage"><a class="page-link" href="#">Previous</a></li>
                                    <li class="page-item" id="nextPage"><a class="page-link" href="#">Next</a></li>
                                </ul>
                            </nav>
                        </div>

                        <script>
                            document.addEventListener('DOMContentLoaded', function () {
                                const productsPerPage = 6;
                                const products = document.querySelectorAll('.product-item');
                                const totalProducts = products.length;
                                const totalPages = Math.ceil(totalProducts / productsPerPage);

                                const pagination = document.getElementById('pagination');
                                const prevPage = document.getElementById('prevPage');
                                const nextPage = document.getElementById('nextPage');

                                function createPageLinks() {
                                    const ul = pagination;
                                    let existingPageNumbers = ul.querySelectorAll('.page-number');
                                    existingPageNumbers.forEach(element => element.remove());

                                    for (let i = 1; i <= totalPages; i++) {
                                        const li = document.createElement('li');
                                        li.classList.add('page-item', 'page-number');
                                        li.id = 'page' + i;

                                        const a = document.createElement('a');
                                        a.classList.add('page-link');
                                        a.href = '#';
                                        a.textContent = i;

                                        li.appendChild(a);
                                        ul.insertBefore(li, nextPage);

                                        a.addEventListener('click', (e) => {
                                            e.preventDefault();
                                            currentPage = i;
                                            showPage(i);
                                        });
                                    }
                                }

                                function showPage(page) {
                                    products.forEach((product, index) => {
                                        if (index >= (page - 1) * productsPerPage && index < page * productsPerPage) {
                                            product.style.display = 'block';
                                        } else {
                                            product.style.display = 'none';
                                        }
                                    });

                                    const pageLinks = pagination.querySelectorAll('.page-number');
                                    pageLinks.forEach(link => link.classList.remove('active'));
                                    const activePageLink = document.getElementById('page' + page);
                                    if (activePageLink) {
                                        activePageLink.classList.add('active');
                                    }

                                    prevPage.classList.toggle('disabled', page === 1);
                                    nextPage.classList.toggle('disabled', page === totalPages);

                                    const url = new URL(window.location.href);
                                    url.searchParams.set('page', page);
                                    window.history.pushState(null, '', url.toString());
                                }

                                prevPage.addEventListener('click', function (e) {
                                    e.preventDefault();
                                    if (currentPage > 1) {
                                        currentPage--;
                                        showPage(currentPage);
                                    }
                                });

                                nextPage.addEventListener('click', function (e) {
                                    e.preventDefault();
                                    if (currentPage < totalPages) {
                                        currentPage++;
                                        showPage(currentPage);
                                    }
                                });


                                createPageLinks();

                                const urlParams = new URLSearchParams(window.location.search);
                                currentPage = parseInt(urlParams.get('page')) || 1;
                                showPage(currentPage);
                            });

                        </script>





                    </div>



                </div>
                <!-- Shop Product End -->
            </div>
        </div>
        <!-- Shop End -->


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