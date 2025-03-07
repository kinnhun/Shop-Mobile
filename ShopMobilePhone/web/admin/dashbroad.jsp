<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>


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
                <!-- row -->
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-xl-12">
                            <div class="row">
                                <div class="col-xl-6">
                                    <div class="row">





                                        <div class="col-xl-12">
                                            <div class="card">
                                                <div class="card-header border-0 flex-wrap">
                                                    <h4 class="fs-20 font-w700 mb-2">Thống kê đơn hàng</h4>
                                                    <div class="d-flex align-items-center project-tab mb-2">	


                                                    </div>	
                                                </div>
                                                <div class="card-body">
                                                    <div class="d-flex justify-content-between align-items-center flex-wrap">
                                                        <div class="d-flex">
                                                            <div class="d-inline-block position-relative donut-chart-sale mb-3">
                                                                <span class="donut1" data-peity='{ "fill": ["rgba(136,108,192,1)", "rgba(241, 234, 255, 1)"],   "innerRadius": 20, "radius": 15}'>${totalOrderPending}/${totalOrder}</span>
                                                            </div>
                                                            <div class="ms-3">
                                                                <h4 class="fs-24 font-w700 ">${totalOrder}</h4>
                                                                <span class="fs-16 font-w400 d-block">Tổng đơn hàng</span>
                                                            </div>
                                                        </div>
                                                        <div class="d-flex">	
                                                            <div class="d-flex me-5">
                                                                <div class="mt-2">
                                                                    <svg width="13" height="13" viewbox="0 0 13 13" fill="none" xmlns="http://www.w3.org/2000/svg">
                                                                    <circle cx="6.5" cy="6.5" r="6.5" fill="#FFCF6D"></circle>
                                                                    </svg>
                                                                </div>
                                                                <div class="ms-3">
                                                                    <h4 class="fs-24 font-w700 ">${totalOrderPending}</h4>
                                                                    <span class="fs-16 font-w400 d-block">Đang chờ xử lý</span>
                                                                </div>
                                                            </div>
                                                            <div class="d-flex">
                                                                <div class="mt-2">
                                                                    <svg width="13" height="13" viewbox="0 0 13 13" fill="none" xmlns="http://www.w3.org/2000/svg">
                                                                    <circle cx="6.5" cy="6.5" r="6.5" fill="#FFA7D7"></circle>
                                                                    </svg>

                                                                </div>
                                                                <div class="ms-3">
                                                                    <h4 class="fs-24 font-w700 ">${totalOrderDelivered}</h4>
                                                                    <span class="fs-16 font-w400 d-block">Dã giao hàng</span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>




                                                </div>
                                            </div>
                                        </div>








                                        <div class="col-xl-12">
                                            <div class="card">
                                                <div class="card-header border-0 pb-0">
                                                    <h4 class="fs-20 font-w700 mb-0">Product Sales by Month</h4>
                                                </div>
                                                <div class="card-body">
                                                    <!-- Chart Container -->
                                                    <canvas id="salesChart"></canvas>
                                                </div>
                                            </div>
                                        </div>

                                        <script>
                                            // Data from the JSP
                                            var labels = [];
                                            var data = [];

                                            // Loop through the productSalesByMonth data and populate labels and data arrays
                                            <c:forEach var="row" items="${productSalesByMonth}">
                                            labels.push("${row[0]}-${row[1]}");  // Year-Month
                                                data.push(${row[2]});  // Total Quantity Sold
                                            </c:forEach>

                                                // Create the chart
                                                var ctx = document.getElementById('salesChart').getContext('2d');
                                                var salesChart = new Chart(ctx, {
                                                    type: 'line', // Can change to 'bar' or other chart types
                                                    data: {
                                                        labels: labels, // X-axis labels (Year-Month)
                                                        datasets: [{
                                                                label: 'Total Quantity Sold',
                                                                data: data, // Y-axis data (Total Quantity Sold)
                                                                borderColor: '#4caf50', // Line color
                                                                fill: false, // No fill under the line
                                                                tension: 0.1  // Smoothness of the line
                                                            }]
                                                    },
                                                    options: {
                                                        responsive: true,
                                                        plugins: {
                                                            legend: {
                                                                position: 'top'
                                                            },
                                                            tooltip: {
                                                                enabled: true
                                                            }
                                                        },
                                                        scales: {
                                                            x: {
                                                                title: {
                                                                    display: true,
                                                                    text: 'Year-Month'
                                                                }
                                                            },
                                                            y: {
                                                                title: {
                                                                    display: true,
                                                                    text: 'Quantity Sold'
                                                                }
                                                            }
                                                        }
                                                    }
                                                });
                                        </script>






                                    </div>

                                </div>
                                <div class="col-xl-6">
                                    <div class="row">
                                        <div class="col-xl-12">
                                            <div class="row">
                                                <div class="col-xl-6 col-sm-6">
                                                    <div class="card">
                                                        <div class="card-body d-flex px-4 pb-0 justify-content-between">
                                                            <div>
                                                                <h4 class="fs-18 font-w600 mb-4 text-nowrap">Tổng người dùng</h4>
                                                                <div class="d-flex align-items-center">
                                                                    <h2 class="fs-32 font-w700 mb-0">${totalUser}</h2>
                                                                    <span class="d-block ms-4">
                                                                        <svg width="21" height="11" viewbox="0 0 21 11" fill="none" xmlns="http://www.w3.org/2000/svg">
                                                                        <path d="M1.49217 11C0.590508 11 0.149368 9.9006 0.800944 9.27736L9.80878 0.66117C10.1954 0.29136 10.8046 0.291359 11.1912 0.661169L20.1991 9.27736C20.8506 9.9006 20.4095 11 19.5078 11H1.49217Z" fill="#09BD3C"></path>
                                                                        </svg>
                                                                        <small class="d-block fs-16 font-w400 text-success">+${totalNewUser / totalUser *100 }%</small>
                                                                    </span>
                                                                </div>
                                                            </div>
                                                            <div id="columnChart"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-sm-6">
                                                    <div class="card">
                                                        <div class="card-body px-4 pb-0">
                                                            <h4 class="fs-18 font-w600 mb-5 text-nowrap">Tổng hàng trong kho</h4>
                                                            <div class="progress default-progress">
                                                                <div class="progress-bar bg-gradient1 progress-animated" style="width: ${totalQuantityToShip / totalQuanityInStock * 100}%; height:10px;" role="progressbar">
                                                                    <span class="sr-only">${totalQuantityToShip / totalQuanityInStock * 100}% Complete</span>
                                                                </div>

                                                            </div>
                                                            <div class="d-flex align-items-end mt-2 pb-3 justify-content-between">
                                                                <span>${totalQuantityToShip} đợi xuất kho</span>
                                                                <h4 class="mb-0">${totalQuanityInStock}</h4>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>


                                            </div>

                                        </div>

                                        <div class="col-xl-12 col-lg-12">
                                            <div class="row">
                                                <div class="col-xl-6 col-xxl-12 col-sm-6">
                                                    <div class="card">
                                                        <div class="card-header border-0">
                                                            <div>
                                                                <h4 class="fs-20 font-w700">Categories</h4>
                                                                <span class="fs-14 font-w400 d-block">Số lựợn sản phẩm</span>
                                                            </div>	
                                                        </div>	




                                                        <div class="card-body">
                                                            <!-- Biểu đồ vòng tròn -->
                                                            <div id="categoryChart"> </div>

                                                            <!-- Legend -->
                                                            <div class="mb-3 mt-4">
                                                                <h4 class="fs-18 font-w600">Legend</h4>
                                                            </div>

                                                            <div>
                                                                <!-- Tạo mảng màu động và gán cho mỗi category -->
                                                                <c:set var="colors" value="[]"/>
                                                                <c:forEach var="category" items="${listc}">
                                                                    <%
                                                                        // Tạo màu ngẫu nhiên cho mỗi category
                                                                        String randomColor = String.format("#%06X", (int) (Math.random() * 0xFFFFFF));
                                                                    %>
                                                                    <c:set var="colors" value="${colors} + ['<%= randomColor %>']" />

                                                                    <div class="d-flex align-items-center justify-content-between mb-4">
                                                                        <span class="fs-18 font-w500">
                                                                            <svg class="me-3" width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
                                                                            <rect width="20" height="20" rx="6" fill="<%= randomColor %>"></rect>
                                                                            </svg>
                                                                            ${category.categoryName} (${category.quanityCategory}%)
                                                                        </span>
                                                                        <span class="fs-18 font-w600">${category.quanityCategory}</span>
                                                                    </div>
                                                                </c:forEach>
                                                            </div>
                                                        </div>

                                                        

                                                        <!-- Mã JavaScript để vẽ biểu đồ -->
                                                        <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
                                                        <script>
                                                var categoryChart = function() {
                                                // Lấy dữ liệu từ phía JSP và chuyển thành mảng JavaScript
                                                var categoryList = [
                                                            <c:forEach var="category" items="${listc}">
                                                {
                                                categoryName: "${category.categoryName}",
                                                        quanityCategory: ${category.quanityCategory}
                                                },
                                                            </c:forEach>
                                                ];
                                                        // Lấy danh sách màu từ JSP và chuyển thành mảng JavaScript
                                                        var colors = [
                                                            <c:forEach var="category" items="${listc}">
                                                                <%
                                                                            // Tạo màu ngẫu nhiên cho mỗi category
                                                                            String randomColor = String.format("#%06X", (int) (Math.random() * 0xFFFFFF));
                                                                %>
                                                        "<%= randomColor %>",
                                                            </c:forEach>
                                                        ];
                                                        // Kiểm tra xem danh sách có dữ liệu không
                                                        console.log(categoryList); // Kiểm tra xem mảng có dữ liệu hay không

                                                        if (categoryList.length > 0) {
                                                // Lấy số lượng category từ danh sách
                                                var seriesData = categoryList.map(function(category) {
                                                return category.quanityCategory; // Mảng giá trị số lượng của từng category
                                                });
                                                        // Lấy tên category để hiển thị trong legend
                                                        var categories = categoryList.map(function(category) {
                                                        return category.categoryName; // Mảng tên category
                                                        });
                                                        // Cấu hình biểu đồ
                                                        var options = {
                                                        series: seriesData, // Sử dụng dữ liệu từ categoryList
                                                                chart: {
                                                                type: 'donut',
                                                                        height: 300
                                                                },
                                                                dataLabels: {
                                                                enabled: false
                                                                },
                                                                stroke: {
                                                                width: 0,
                                                                },
                                                                colors: colors, // Màu sắc được áp dụng cho từng category
                                                                legend: {
                                                                position: 'bottom',
                                                                        show: true,
                                                                        labels: {
                                                                        useSeriesColors: true
                                                                        }
                                                                },
                                                                labels: categories, // Hiển thị tên các category trong legend
                                                                responsive: [{
                                                                breakpoint: 1800,
                                                                        options: {
                                                                        chart: {
                                                                        height: 200
                                                                        },
                                                                        }
                                                                }]
                                                        };
                                                        // Khởi tạo biểu đồ với ID là categoryChart
                                                        var chart = new ApexCharts(document.querySelector("#categoryChart"), options);
                                                        chart.render();
                                                } else {
                                                console.error("Không có dữ liệu để hiển thị biểu đồ.");
                                                }
                                                };
                                                        // Gọi hàm để vẽ biểu đồ
                                                        categoryChart();
                                                        </script>








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
            <script src="vendor/jquery-nice-select/js/jquery.nice-select.min.js"></script>

            <!-- Apex Chart -->
            <script src="vendor/apexchart/apexchart.js"></script>

            <script src="vendor/chart.js/Chart.bundle.min.js"></script>

            <!-- Chart piety plugin files -->
            <script src="vendor/peity/jquery.peity.min.js"></script>
            <!-- Dashboard 1 -->
            <script src="js/dashboard/dashboard-1.js"></script>

            <script src="vendor/owl-carousel/owl.carousel.js"></script>

            <script src="js/custom.min.js"></script>
            <script src="js/dlabnav-init.js"></script>
            <script src="js/demo.js"></script>
            <script src="js/styleSwitcher.js"></script>
            <script>
                                                        function cardsCenter()
                                                        {

                                                        /*  testimonial one function by = owl.carousel.js */



                                                        jQuery('.card-slider').owlCarousel({
                                                        loop: true,
                                                                margin: 0,
                                                                nav: true,
                                                                //center:true,
                                                                slideSpeed: 3000,
                                                                paginationSpeed: 3000,
                                                                dots: true,
                                                                navText: ['<i class="fas fa-arrow-left"></i>', '<i class="fas fa-arrow-right"></i>'],
                                                                responsive: {
                                                                0: {
                                                                items: 1
                                                                },
                                                                        576: {
                                                                        items: 1
                                                                        },
                                                                        800: {
                                                                        items: 1
                                                                        },
                                                                        991: {
                                                                        items: 1
                                                                        },
                                                                        1200: {
                                                                        items: 1
                                                                        },
                                                                        1600: {
                                                                        items: 1
                                                                        }
                                                                }
                                                        })
                                                        }

                                                jQuery(window).on('load', function () {
                                                setTimeout(function () {
                                                cardsCenter();
                                                }, 1000);
                                                });

            </script>

    </body>
</html>