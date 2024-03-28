<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Votre Application</title>
    <link href="resources/style/styles.css" rel="stylesheet">
</head>
<body>
<div class="header">

    <div class="container">



        <div class="navbar">
            <a href="${pageContext.request.contextPath}/index.jsp">Accueil</a>
            <a href="${pageContext.request.contextPath}/ingoing">Jeux en cours</a>
            <a href="${pageContext.request.contextPath}/leader">Leaderboard</a>

            <c:if test="${sessionScope.sessionUser == null}">
                <a href="${pageContext.request.contextPath}/login">Connexion</a>
                <a href="${pageContext.request.contextPath}/register">Inscription</a>
            </c:if>
            <c:if test="${sessionScope.sessionUser != null}">
                <a href="${pageContext.request.contextPath}/games">Historique</a>
                <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
                ${sessionScope.sessionUser.getName()}
                <form action="${pageContext.request.contextPath}/deleteUser" method="post">
                    <input type="hidden" name="id" value="${sessionScope.sessionUser.id}">
                    <button type="submit" onclick="return confirm('Êtes-vous sûr de vouloir supprimer votre compte ? Cette action est irréversible.');">Supprimer mon compte</button>
                </form>
            </c:if>
        </div>
    </div>
</div>
