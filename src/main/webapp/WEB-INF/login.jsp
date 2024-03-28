<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"/>
<h2>Connexion</h2>

<div class="login-container">
    <form action="login" method="POST" class="form">
        <div class="title">Welcome,<br><span>sign up to continue</span></div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" placeholder="Email" name="email" class="input" id="email" required="required">
            <c:if test="${not empty loginForm.erreurs['email']}">
                <span class="error">${loginForm.erreurs['email']}</span>
            </c:if>
        </div>
        <div class="form-group">
            <label for="motdepasse">Mot de passe:</label>
            <input type="password" placeholder="Password" name="motdepasse" class="input" id="motdepasse" required="required">
            <c:if test="${not empty loginForm.erreurs['motdepasse']}">
                <span class="error">${loginForm.erreurs['motdepasse']}</span>
            </c:if>
        </div>
        <button class="button-confirm">Let`s go →</button>
        <c:if test="${not empty loginForm.resultat}">
            <div class="${loginForm.erreurs.isEmpty() ? 'success' : 'error'}">
                    ${loginForm.resultat}
            </div>
        </c:if>
    </form>
</div>
<jsp:include page="footer.jsp"/>
