<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <title>Yahoo!!</title>
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h2>Add a todo</h2>
            <form:form method="post" modelAttribute="todo">
                <form:hidden path="id"/>
                <fieldset class="form-group">
                    <form:label path="desc">Description</form:label>
                    <form:input path="desc" type="text" class="form-control" required="required"/>
                    <form:errors path="desc" cssClass="text-warning" />
                </fieldset>

                <input class="btn-success" name="submit" type="submit"/>
            </form:form>
        </div>
        <script src="${pageContext.request.contextPath}/webjars/jquery/3.6.0/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
    </body>
</html>