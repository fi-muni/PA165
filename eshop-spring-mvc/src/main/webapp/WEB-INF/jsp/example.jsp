<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message var="title" key="example.title"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">

    <p class="alert alert-warning"><fmt:message key="example.text1"/></p>
    <table class="table table-bordered">
        <thead>
        <tr><th><fmt:message key="example.attribute"/></th><th><fmt:message key="example.value"/></th></tr>
        </thead>
        <tbody>
        <tr><th>a</th><td><c:out value="${a}"/></td></tr>
        <tr><th>r1</th><td><c:out value="${r1}"/></td></tr>
        <tr><th>r2</th><td><c:out value="${r2}"/></td></tr>
        <tr><th>b</th><td><c:out value="${b}"/></td></tr>
        <tr><th>locale</th><td><c:out value="${locale}"/></td></tr>
        <tr><th>httpMethod</th><td><c:out value="${httpMethod}"/></td></tr>
        <tr><th>userAgent</th><td><c:out value="${userAgent}"/></td></tr>
        <tr>
            <th>session_cookie</th>
            <td><c:if test="${not empty session_cookie}"> <c:out value="${session_cookie.name}"/>=<c:out value="${session_cookie.value}"/></c:if></td>
        </tr>
        </tbody>
    </table>

     <p><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/example/foo/1/platypus55?b=42&redir=true"
           role="button"><fmt:message key="example.redirect.button"/></a></p>
</jsp:attribute>
</my:pagetemplate>