<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="shopping.show.title"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">

    <c:forEach items="${categories}" var="category" varStatus="ic">
        <h2>${ic.count}. <c:out value="${category.name}"/></h2>
        <div class="row">
        <c:choose>
            <c:when test="${empty cat2prods[category.id]}">
                <div class="col-xs-12">
                This category is empty.
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach items="${cat2prods[category.id]}" var="product">
                    <div class="col-xs-12 col-sm-4 col-md-3 col-lg-2"><!-- bootstrap responsive grid -->
                        <a href="${pageContext.request.contextPath}/shopping/product/${product.id}">
                        <div class="thumbnail">
                        <img src="${pageContext.request.contextPath}/shopping/productImage/${product.id}"><br>
                        <div class="caption">
                            <h3><c:out value="${product.name}"/></h3>
                            <span style="color: red; font-weight: bold;"><c:out value="${product.currentPrice.value}"/>&nbsp;<c:out value="${product.currentPrice.currency}"/></span>
                        </div>
                        </div>
                        </a>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </div>
    </c:forEach>

</jsp:attribute>
</my:pagetemplate>