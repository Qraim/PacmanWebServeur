<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"/>
<div class="login-container">
    <h2>Inscription</h2>
    <form action="${pageContext.request.contextPath}/register" method="POST">
        <div class="form-group">
            <label for="email">Adresse email:</label>
            <input type="email" name="email" id="email" value="<c:out value="${User.email}"/>" required="required" size="20" maxlength="60" />
            <c:if test="${not empty Forms.erreurs['email']}">
                <span class="error">${Forms.erreurs['email']}</span>
            </c:if>
        </div>
        <div class="form-group">
            <label for="motdepasse">Mot de passe:</label>
            <input type="password" name="motdepasse" id="motdepasse" required="required" size="20" maxlength="20" />
            <c:if test="${not empty Forms.erreurs['motdepasse']}">
                <span class="error">${Forms.erreurs['motdepasse']}</span>
            </c:if>
        </div>
        <div class="form-group">
            <label for="confirmation">Confirmation du mot de passe:</label>
            <input type="password" name="confirmation" id="confirmation" required="required" size="20" maxlength="20" />
            <c:if test="${not empty Forms.erreurs['confirmation']}">
                <span class="error">${Forms.erreurs['confirmation']}</span>
            </c:if>
        </div>
        <div class="form-group">
            <label for="nom">Nom d'utilisateur:</label>
            <input type="text" name="nom" id="nom" required="required" value="<c:out value="${User.name}"/>" size="20" maxlength="20" />
            <c:if test="${not empty Forms.erreurs['nom']}">
                <span class="error">${Forms.erreurs['nom']}</span>
            </c:if>
        </div>
        <div class="form-group">
            <input type="submit" value="Inscription" />
        </div>
        <c:if test="${not empty Forms.resultat}">
            <div class="${empty Forms.erreurs ? 'success' : 'error'}">
                    ${Forms.resultat}
            </div>
        </c:if>
    </form>
</div>
<jsp:include page="footer.jsp"/>
