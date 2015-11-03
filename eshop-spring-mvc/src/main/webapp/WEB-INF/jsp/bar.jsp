<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Bar">
<jsp:attribute name="body">

    <table class="table table-bordered">
        <thead>
        <tr><th><fmt:message key="example.attribute"/></th><th><fmt:message key="example.value"/></th></tr>
        </thead>
        <tbody>
        <tr><th>c</th><td><c:out value="${c}"/></td></tr>
        <tr><th>d</th><td><c:out value="${d}"/></td></tr>
        <tr><th>e</th><td><c:out value="${e}"/></td></tr>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>