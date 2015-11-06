<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Product Administration">
<jsp:attribute name="body">

    <form method="post" action="${pageContext.request.contextPath}/product/delete/${product.id}">
        <button type="submit" class="btn btn-primary">Delete</button>
    </form>


    <table class="table">
        <thead>
        <tr>
            <th>id</th>
            <th>added</th>
            <th>product name</th>
            <th>description</th>
            <th>color</th>
            <th>price</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>${product.id}</td>
                <td><fmt:formatDate value="${product.addedDate}" pattern="yyyy-MM-dd"/></td>
                <td><c:out value="${product.name}"/></td>
                <td><c:out value="${product.description}"/></td>
                <td><c:out value="${product.color}"/></td>
                <td><c:out value="${product.currentPrice.value} ${product.currentPrice.currency}"/></td>
            </tr>
        </tbody>
    </table>

        <div class="row">
            <div class="col-xs-6">
                    <div class="panel panel-default">
                    <div class="panel-body">
                <img class="img-responsive img-rounded"
                     src="${pageContext.request.contextPath}/shopping/productImage/${product.id}">
                    </div>
                    </div>
            </div>
            <div class="col-xs-6">
                <table class="table">
                    <caption>Historic prices</caption>
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>Price</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${product.priceHistory}" var="price">
                        <tr>
                            <td><fmt:formatDate value="${price.priceStart}" type="date" dateStyle="medium"/></td>
                            <td><c:out value="${price.value}"/>&nbsp;<c:out value="${price.currency}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

</jsp:attribute>
</my:pagetemplate>