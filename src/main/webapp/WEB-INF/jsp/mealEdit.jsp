<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h2><a href="">Home</a></h2>
    <h3>Edit meal</h3>
    <hr>

    <form:form method="post" modelAttribute="mealForm" action="meals">
        <form:hidden path="id" />
        <dl>
            <dt>DateTime:</dt>
            <dd><form:input path="dateTime" type="datetime-local"  /></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><form:input path="description" /></dd>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <dd><form:input path="calories" type="number" /></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()">Cancel</button>
    </form:form>
</section>
</body>
</html>
