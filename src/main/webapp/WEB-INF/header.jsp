<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Votre Application</title>
    <link href="resources/styles.css" rel="stylesheet">
</head>
<body>
<div class="header">
    <div class="container">
        <div class="navbar">
            <a href="${pageContext.request.contextPath}/index.jsp">Accueil</a>
            <c:if test="${sessionScope.sessionUser == null}">
                <a href="${pageContext.request.contextPath}/login">Connexion</a>
                <a href="${pageContext.request.contextPath}/register">Inscription</a>
            </c:if>
            <c:if test="${sessionScope.sessionUser != null}">
                Bonjour, ${sessionScope.sessionUser.getName()}
                <a href="${pageContext.request.contextPath}/games">Historique</a>
                <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
            </c:if>
        </div>
    </div>
</div>
