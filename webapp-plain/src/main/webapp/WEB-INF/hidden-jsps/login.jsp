<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:message var="title" key="login.title"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">

<form method="POST" action="j_security_check">
    <table>
        <tr>
            <td colspan="2"><fmt:message key="login.heading"/></td>
        </tr>
        <tr>
            <td><label for="username-label"><fmt:message key="login.username"/></label></td>
            <td><input id="username-label" type="text" name="j_username"></td>
        </tr>
        <tr>
            <td><label for="password-label"><fmt:message key="login.password"/></label></td>
            <td><input id="password-label" type="password" name="j_password" ></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit"></td>
        </tr>
    </table>
</form>

</jsp:attribute>
</my:pagetemplate>
