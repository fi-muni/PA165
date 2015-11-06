<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Order ${order.id} detail">
<jsp:attribute name="body">


        <table class="table">
            <caption>Order</caption>
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
            <tr>
                <td>${order.id}</td>
                <td><fmt:formatDate value="${order.created}" pattern="yyyy-MM-dd"/></td>
                <td><b style="color: red;">${order.state}</b></td>
                <td><c:out value="${order.user.email}"/></td>
                <td><c:out value="${order.user.givenName} ${order.user.surname}"/></td>
                <td><c:out value="${order.user.address}"/></td>
                <td><c:out value="${order.user.phone}"/></td>
            </tr>
            </tbody>
        </table>

    <div class="row">
    <c:choose>
        <c:when test="${order.state=='RECEIVED'}">
            <div class="col-xs-1">
            <form method="post" action="${pageContext.request.contextPath}/order/ship/${order.id}">
                <button type="submit" class="btn btn-primary">Ship</button>
            </form>
            </div>
            <div class="col-xs-1">
            <form method="post" action="${pageContext.request.contextPath}/order/cancel/${order.id}">
                <button type="submit" class="btn btn-primary">Cancel</button>
            </form>
            </div>
        </c:when>
        <c:when test="${order.state=='SHIPPED'}">
            <div class="col-xs-1">
            <form method="post" action="${pageContext.request.contextPath}/order/finish/${order.id}">
                <button type="submit" class="btn btn-primary">Finish</button>
            </form>
            </div>
        </c:when>
    </c:choose>
    </div>

    <table class="table">
        <caption>order items</caption>
        <thead>
        <tr>
            <th>id</th>
            <th>amount</th>
            <th>product</th>
            <th>price per product</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${order.orderItems}" var="item">
            <tr>
                <td>${item.id}</td>
                <td><c:out value="${item.amount}"/>x</td>
                <td><c:out value="${item.product.name}"/></td>
                <td><c:out value="${item.pricePerItem.value} ${item.pricePerItem.currency}"/></td>
            </tr>
        </c:forEach>
        <tr>
            <th colspan="3"><b>Total</b></th>
            <th>${totalPrice} ${totalCurrency}</th>
        </tr>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>