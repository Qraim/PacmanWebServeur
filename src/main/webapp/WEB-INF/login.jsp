<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"/>
<div class="login-container">
    <h2>Connexion</h2>
    <form action="login" method="POST">
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

        <c:if test="${not empty loginForm.resultat}">
            <div class="${loginForm.erreurs.isEmpty() ? 'success' : 'error'}">
                    ${loginForm.resultat}
            </div>
        </c:if>
    </form>

</div>
</body>
<jsp:include page="footer.jsp"/>
