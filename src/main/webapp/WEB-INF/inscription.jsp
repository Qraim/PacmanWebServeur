<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"/>
<div class="title">Inscription</div>

<div class="login-container">
    <form class="form" action="${pageContext.request.contextPath}/register" method="POST">
        <div class="form-group">
            <label for="email" class="visually-hidden">Adresse email:</label>
            <input type="email" name="email" id="email" placeholder="Email" value="<c:out value='${User.email}'/>" required="required" class="input" />
            <c:if test="${not empty Forms.erreurs['email']}">
                <span class="error">${Forms.erreurs['email']}</span>
            </c:if>
        </div>
        <div class="form-group">
            <label for="motdepasse" class="visually-hidden">Mot de passe:</label>
            <input type="password" name="motdepasse" id="motdepasse" placeholder="Mot de passe" required="required" class="input" />
            <c:if test="${not empty Forms.erreurs['motdepasse']}">
                <span class="error">${Forms.erreurs['motdepasse']}</span>
            </c:if>
        </div>
        <div class="form-group">
            <label for="confirmation" class="visually-hidden">Confirmation du mot de passe:</label>
            <input type="password" name="confirmation" id="confirmation" placeholder="Confirmation du mot de passe" required="required" class="input" />
            <c:if test="${not empty Forms.erreurs['confirmation']}">
                <span class="error">${Forms.erreurs['confirmation']}</span>
            </c:if>
        </div>
        <div class="form-group">
            <label for="nom" class="visually-hidden">Nom d'utilisateur:</label>
            <input type="text" name="nom" id="nom" placeholder="Nom d'utilisateur" value="<c:out value='${User.name}'/>" class="input" />
            <c:if test="${not empty Forms.erreurs['nom']}">
                <span class="error">${Forms.erreurs['nom']}</span>
            </c:if>
        </div>
        <button class="button-confirm">Inscription</button>
        <c:if test="${not empty Forms.resultat}">
            <div class="${empty Forms.erreurs ? 'success' : 'error'}">
                    ${Forms.resultat}
            </div>
        </c:if>
    </form>
</div>
<jsp:include page="footer.jsp"/>
