<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Leaderboard</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>Leaderboard</h1>

<c:if test="${not empty topUsers}">
    <table>
        <thead>
        <tr>
            <th>Rank</th>
            <th>User ID</th>
            <th>Score</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${topUsers}" var="userScore" varStatus="status">
            <tr>
                <td>${status.index + 1}</td>
                <c:choose>
                    <c:when test="${not empty userScore.split('-')[0]}">
                        <td>${userScore.split('-')[0]}</td>
                        <td>${userScore.split('-')[1]}</td>
                    </c:when>
                    <c:otherwise>
                        <td colspan="2">Data not available</td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty topUsers}">
    <p>No leaderboard data found.</p>
</c:if>

<jsp:include page="footer.jsp"/>
</body>
</html>
