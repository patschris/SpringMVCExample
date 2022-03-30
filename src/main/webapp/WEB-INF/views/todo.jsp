<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

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

<%@ include file="common/footer.jspf"%>