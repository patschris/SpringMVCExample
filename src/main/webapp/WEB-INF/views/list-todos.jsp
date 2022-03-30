<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
    <head>
        <title>Yahoo!!</title>
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            Hi, ${name}. <br/>
            Your todos are: <br/>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Description</th>
                    <th>Date</th>
                    <th>Is Completed?</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${todos}" var="todo">
                    <tr>
                        <td>${todo.desc}</td>
                        <td><fmt:formatDate pattern="dd/MM/yyyy" value="${todo.targetDate}" /></td>
                        <td>${todo.done}</td>
                        <td>
                            <a class="btn btn-info" href="/update-todo/${todo.id}">Update</a>
                            <a class="btn btn-danger" href="/delete-todo/${todo.id}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>


            <div>
                <a class="btn btn-success" href="<c:url value="/add-todo"/>">Add</a>
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/webjars/jquery/3.6.0/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
    </body>
</html>