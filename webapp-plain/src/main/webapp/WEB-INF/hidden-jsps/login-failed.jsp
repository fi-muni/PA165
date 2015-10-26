<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:message var="title" key="login.title"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">

<div style="border: 1px solid red; background-color: lightcoral; padding: 1em;">
    <fmt:message key="login.failed"/>
</div>

</jsp:attribute>
</my:pagetemplate>
