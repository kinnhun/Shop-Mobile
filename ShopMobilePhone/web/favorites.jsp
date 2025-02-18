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
                        <span class="breadcrumb-item active">Shopping favorites </span>
                    </nav>
                </div>
            </div>
        </div>
        <!-- Breadcrumb End -->


        <!-- Cart Start -->
        <div class="container-fluid">
            <div class="row px-xl-5">





                <div class="col-lg-8 table-responsive mb-5">
                    <table id="favoritesTable"  class="align-middle table table-light table-borderless table-hover text-center mb-0">
                        <thead class="thead-dark">
                            <tr>
                                <th>Sản phẩm </th>
                                <th>Giá</th>
                                <th>Bỏ yêu thích</th>
                            </tr>
                        </thead>
                        <tbody class="align-middle">
                            <c:choose>
                                <c:when test="${empty listFavorites}">
                                    <tr>
                                        <td colspan="3" class="text-center">Không có sản phẩm nào trong danh sách yêu thích.</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="listFavorites" items="${listFavorites}">
                                        <tr>
                                            <td class="align-middle">
                                                <img src="${listFavorites.productId.productImage}" alt="" style="width: 50px;">
                                                ${listFavorites.productId.name}
                                            </td>
                                            <td class="align-middle">${listFavorites.productId.price} vnd</td>  
                                    <script>

                                    </script>
                                    <td class="align-middle">
                                        <form action="favorites" method="post" onsubmit="return confirmDelete()">
                                            <input name="action" value="remove" hidden="">
                                            <input name="favoriteId" value="${listFavorites.favoriteId}" hidden="">
                                            <button type="submit" class="btn btn-sm btn-danger">
                                                <i class="fa fa-times"></i>
                                            </button>
                                        </form>
                                    </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>

                        <script>
                            function confirmDelete() {
                                return confirm("Bạn có chắc chắn muốn xóa sản phẩm này khỏi danh sách yêu thích?");
                            }
                        </script>



                        </tbody>
                    </table>


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
                            const productsPerPage = 5;
                            let currentPage = 1;

                            // Lấy danh sách các hàng trong bảng (bỏ qua hàng tiêu đề)
                            const products = document.querySelectorAll('#favoritesTable tbody tr:not(:first-child)');
                            const totalProducts = products.length;
                            const totalPages = Math.ceil(totalProducts / productsPerPage);

                            const pagination = document.getElementById('pagination');
                            const prevPage = document.getElementById('prevPage');
                            const nextPage = document.getElementById('nextPage');

                            function createPageLinks() {
                                let existingPageNumbers = pagination.querySelectorAll('.page-number');
                                existingPageNumbers.forEach(element => element.remove());

                                for (let i = 1; i <= totalPages; i++) {
                                    const li = document.createElement('li');
                                    li.classList.add('page-item', 'page-number');
                                    if (i === currentPage)
                                        li.classList.add('active');
                                    li.id = 'page' + i;

                                    const a = document.createElement('a');
                                    a.classList.add('page-link');
                                    a.href = '#';
                                    a.textContent = i;

                                    li.appendChild(a);
                                    pagination.insertBefore(li, nextPage);

                                    a.addEventListener('click', (e) => {
                                        e.preventDefault();
                                        currentPage = i;
                                        showPage(currentPage);
                                    });
                                }
                            }

                            function showPage(page) {
                                products.forEach((product, index) => {
                                    if (index >= (page - 1) * productsPerPage && index < page * productsPerPage) {
                                        product.style.display = 'table-row';
                                    } else {
                                        product.style.display = 'none';
                                    }
                                });

                                document.querySelectorAll('.page-number').forEach(link => link.classList.remove('active'));
                                const activePageLink = document.getElementById('page' + page);
                                if (activePageLink)
                                    activePageLink.classList.add('active');

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