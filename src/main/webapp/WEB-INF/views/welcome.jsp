<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
    Welcome ${name}.<br/>
    Now, you <a href="<c:url value="/list-todos"/>">can manage your todos</a>.
</div>

<%@ include file="common/footer.jspf"%>