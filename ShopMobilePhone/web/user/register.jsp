<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Register - MultiShop</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="img/favicon.ico" rel="icon">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
    <c:import url="./header.jsp" />

    <div class="container mt-4">
        <h2 class="text-center text-uppercase mb-4">Register</h2>
        <%@ include file="./notification.jsp" %>

        <form action="login-register?register=true" method="post" id="registerForm" class="bg-white p-4 shadow rounded" enctype="multipart/form-data">
            <input type="hidden" name="action" value="register">
            
            <div class="row">
                <div class="col-lg-6">
                    <div class="mb-3">
                        <label class="form-label fw-bold">Full Name</label>
                        <input type="text" class="form-control" name="full_name" required>
                    </div>
                   <div class="mb-3">
                        <label class="form-label fw-bold">Phone</label>
                        <input type="text" class="form-control" name="phone" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-bold">Password</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-bold">Confirm Password</label>
                        <input type="password" class="form-control" id="confirmPassword" required>
                        <small id="passwordError" class="text-danger"></small>
                    </div>
                </div>
                
                <div class="col-lg-6">
                    
                    <div class="mb-3">
                        <label class="form-label fw-bold">Email</label>
                        <input type="email" class="form-control" name="email" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-bold">Address</label>
                        <input type="text" class="form-control" name="address" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label fw-bold">Avatar</label>
                        <input type="file" class="form-control" id="avatar" name="avatar" accept="image/*">
                        <img id="avatarPreview" class="img-thumbnail mt-2" style="display:none; max-width: 150px;">
                    </div>
                </div>
            </div>
            
            <button class="btn btn-primary w-100" type="submit">Register</button>
            <div class="text-center mt-3">
                <a href="login-register">Already have an account? Login</a>
            </div>
        </form>
    </div>

    <c:import url="./footer.jsp" />

    <script>
        document.getElementById('avatar').addEventListener('change', function (event) {
            const file = event.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    document.getElementById('avatarPreview').src = e.target.result;
                    document.getElementById('avatarPreview').style.display = 'block';
                }
                reader.readAsDataURL(file);
            }
        });

        document.getElementById('registerForm').addEventListener('submit', function (e) {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            const passwordError = document.getElementById('passwordError');
            if (password !== confirmPassword) {
                e.preventDefault();
                passwordError.textContent = 'Passwords do not match!';
            } else {
                passwordError.textContent = '';
            }
        });
    </script>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
