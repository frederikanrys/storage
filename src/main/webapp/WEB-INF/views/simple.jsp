<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Simple view</title>
</head>

<body>
<ul>
    <li><a href="${parent.url}"> .. </a></li>

    <c:forEach var="resource" items="${resources}">
        <c:if test="${resource.type == 'directory'}">
            <li><a href="${resource.url}">${resource.name}/</a></li>
        </c:if>
    </c:forEach>

    <c:forEach var="resource" items="${resources}">
        <c:if test="${resource.type == 'file'}">
            <li><a href="${resource.url}"> ${resource.name} </a></li>
        </c:if>
    </c:forEach>


</ul>

</body>
</html>

