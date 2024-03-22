<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="Modele.Game" %>
<jsp:include page="header.jsp"/>
<body>
<h1>Liste des parties en cours</h1>

<c:if test="${not empty games}">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Map</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${games}" var="game">
            <tr>
                <td>${game.id}</td>
                <td>${game.map}</td>
                <td>${game.status}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty games}">
    <p>Aucune partie trouvée.</p>
</c:if>
</body>
<jsp:include page="footer.jsp"/>
