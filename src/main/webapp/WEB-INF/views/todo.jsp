<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
    <h2>Add a todo</h2>
<%--    If there is no action, the request is sent by default to the same url that the request came from.
        So, /add-todo but with post method--%>
    <form:form method="post" modelAttribute="todo">
        <form:hidden path="id"/>
        <fieldset class="form-group">
            <form:label path="description">Description</form:label>
            <form:input path="description" type="text" class="form-control" required="required"/>
            <form:errors path="description" cssClass="text-warning"/>
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

<%@ include file="common/footer.jspf"%>