<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

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
                <td>${todo.description}</td>
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
        <a class="btn btn-success" href="/add-todo">Add</a>
    </div>
</div>

<%@ include file="common/footer.jspf"%>