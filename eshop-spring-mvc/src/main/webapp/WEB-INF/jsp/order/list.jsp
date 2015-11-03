<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Orders">
<jsp:attribute name="body">

    <div class="btn-group" role="group" aria-label="filter">
        <my:a href="/order/list/all" class="btn btn-default ${filter=='all'?'active':''}">All</my:a>
        <my:a href="/order/list/received" class="btn btn-default ${filter=='received'?'active':''}">Received</my:a>
        <my:a href="/order/list/shipped" class="btn btn-default ${filter=='shipped'?'active':''}">Shipped</my:a>
        <my:a href="/order/list/canceled" class="btn btn-default ${filter=='canceled'?'active':''}">Canceled</my:a>
        <my:a href="/order/list/done" class="btn btn-default ${filter=='done'?'active':''}">Done</my:a>
        <my:a href="/order/list/unprocessed" class="btn btn-default ${filter=='unprocessed'?'active':''}">Unprocessed</my:a>
    </div>

    <table class="table">
        <thead>
        <tr>
            <th>id</th>
            <th>placed</th>
            <th>state</th>
            <th>email</th>
            <th>customer name</th>
            <th>address</th>
            <th>phone</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.id}</td>
                <td><fmt:formatDate value="${order.created}" pattern="yyyy-MM-dd"/></td>
                <td>${order.state}</td>
                <td><c:out value="${order.user.email}"/></td>
                <td><c:out value="${order.user.givenName} ${order.user.surname}"/></td>
                <td><c:out value="${order.user.phone}"/></td>
                <td><c:out value="${order.user.address}"/></td>
                <td>
                    <my:a href="/order/detail/${order.id}" class="btn btn-primary">View</my:a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>