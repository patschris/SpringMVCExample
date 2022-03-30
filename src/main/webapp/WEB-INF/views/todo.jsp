<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <title>Yahoo!!</title>
        <link href="${pageContext.request.contextPath}/webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://formden.com/static/cdn/bootstrap-iso.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
    </head>
    <body>
        <div class="container">
            <h2>Add a todo</h2>
            <form:form method="post" modelAttribute="todo">
                <form:hidden path="id"/>
                <fieldset class="form-group">
                    <form:label path="desc">Description</form:label>
                    <form:input path="desc" type="text" class="form-control" required="required"/>
                    <form:errors path="desc" cssClass="text-warning"/>
                </fieldset>
                <fieldset class="form-group">
                    <form:label path="targetDate">Target Date</form:label>
                    <form:input path="targetDate" type="text" class="form-control datepicker3" required="required" />
                    <form:errors path="targetDate" cssClass="text-warning" />
                </fieldset>
                <br/>
                <input class="btn-success" name="submit" type="submit"/>
            </form:form>
        </div>
        <script src="${pageContext.request.contextPath}/webjars/jquery/3.6.0/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/webjars/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
        <script>
            $(document).ready(function(){
                const date_input = $('input[name="targetDate"]');
                const sel = $('.bootstrap-iso form');
                const container = sel.length > 0 ? sel.parent() : "body";
                const options = {
                    format: 'dd/mm/yyyy',
                    startDate: 'd',
                    container: container,
                    todayHighlight: true,
                    autoclose: true
                };
                date_input.datepicker(options);
            })
        </script>
    </body>
</html>