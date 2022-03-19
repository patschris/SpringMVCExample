<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Yahoo!!</title>
</head>
<body>
Hi, ${name}. <br/>
Your todos are: <br/>
<table>
    <thead>
        <tr>
            <th>Description</th>
            <th>Date</th>
            <th>Is Completed?</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${todos}" var="todo">
        <tr>
            <td>${todo.desc}</td>
            <td>${todo.targetDate} </td>
            <td >${todo.done}</td>
        </tr>
        </c:forEach>
    </tbody>
</table>



<a class="button" href="/add-todo">Add</a>
</body>
</html>