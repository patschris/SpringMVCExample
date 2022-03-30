<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
    <p><span style="color: red;">${errorMessage}</span></p>
    <form action="<c:url value="/login"/>" method="POST">
        <fieldset class="form-group">
            <label>Name</label>
            <input name="name" type="text" class="form-control" required="required"/>
        </fieldset>
        <fieldset class="form-group">
            <label>Password</label>
            <input name="password" type="password" class="form-control" required="required"/>
        </fieldset>
        <br/>
        <button type="submit" class="btn btn-success">Submit</button>
    </form>
</div>

<%@ include file="common/footer.jspf"%>