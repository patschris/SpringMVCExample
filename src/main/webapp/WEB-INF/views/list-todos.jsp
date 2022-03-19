<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Yahoo!!</title>
        <link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
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
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${todos}" var="todo">
                    <tr>
                        <td>${todo.desc}</td>
                        <td>${todo.targetDate} </td>
                        <td >${todo.done}</td>
                        <td><a class="btn btn-danger" href="/delete-todo/${todo.id}">Delete</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>


            <div>
                <a class="btn btn-success" href="/add-todo">Add</a>
            </div>
        </div>

        <script src="webjars/jquery/3.6.0/jquery.min.js"></script>
        <script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
    </body>
</html>