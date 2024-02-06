<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<div class="login-container">
    <h2>Connexion</h2>
    <form action="${pageContext.request.contextPath}/login" method="POST">
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" name="email" id="email" required="required" />
            <c:if test="${not empty loginForm.erreurs['email']}">
                <span class="error">${loginForm.erreurs['email']}</span>
            </c:if>
        </div>
        <div class="form-group">
            <label for="motdepasse">Mot de passe:</label>
            <input type="password" name="motdepasse" id="motdepasse" required="required" />
            <c:if test="${not empty loginForm.erreurs['motdepasse']}">
                <span class="error">${loginForm.erreurs['motdepasse']}</span>
            </c:if>
        </div>
        <div class="form-group">
            <input type="submit" value="Connexion" />
        </div>
    </form>
    <c:if test="${not empty loginForm.resultat}">
        <div class="${loginForm.erreurs.isEmpty() ? 'success' : 'error'}">
                ${loginForm.resultat}
        </div>
    </c:if>
</div>
</body>
</html>
