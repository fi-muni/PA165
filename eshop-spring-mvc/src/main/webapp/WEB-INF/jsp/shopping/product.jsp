<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="shopping.product.title"><fmt:param value="${product.name}"/></fmt:message>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">

    <div class="panel panel-default">
        <div class="panel-heading">In categories</div>
        <div class="panel-body">
            <c:forEach items="${product.categories}" var="category">
                <a href="${pageContext.request.contextPath}/shopping/category/${category.id}" class="btn btn-info">
                    <c:out value="${category.name}"/>
                </a>
            </c:forEach>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-6">
            <h3><c:out value="${product.name}"/></h3>
            <p>
                <c:out value="${product.description}"/>
            </p>
            Current price: <span style="color: red; font-weight: bold;"><c:out value="${product.currentPrice.value}"/>&nbsp;<c:out
                value="${product.currentPrice.currency}"/></span>
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

    <div class="row">
        <div class="col-xs-12">
            <%--<div class="panel panel-default">--%>
                <%--<div class="panel-body">--%>
                    <img class="img-responsive img-rounded"
                         src="${pageContext.request.contextPath}/shopping/productImage/${product.id}">
                <%--</div>--%>
            <%--</div>--%>
        </div>
    </div>
</jsp:attribute>
</my:pagetemplate>