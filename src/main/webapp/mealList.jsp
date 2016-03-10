<%--
  Created by IntelliJ IDEA.
  User: propant
  Date: 09.03.2016
  Time: 12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>List of Meals</title>
</head>
<body>
    <h1>List of Meals</h1>

    <ul>
        <c:forEach var="meal" items="${meals}">
           <c:choose>
                <c:when test="${meal.exceed}">
                    <li style="color:red;">

                </c:when>
               <c:otherwise>
                   <li style="color:green;">
               </c:otherwise>

            </c:choose>
                <%--<c:out value="${meal.dateTime}" />&nbsp;
                <c:out value="${meal.description}" />&nbsp;
                <c:out value="${meal.calories}" />&nbsp;--%>
                <form action="/topjava/meals" method="POST">
                    <input type="hidden" value="${meal.id}" name="id" />
                    <input type="text" value="${meal.dateTime}" name="dateTime" />&nbsp;
                    <input type="text" value="${meal.description}" name="description" />&nbsp;
                    <input type="text" value="${meal.calories}" name="calories" />&nbsp;&nbsp;
                    <input type="submit" value="Записать" />
                </form>
            </li>
        </c:forEach>
    </ul>


</body>
</html>
