<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:message var="title" key="praha.title"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">

  <p><fmt:message key="praha.text"/> </p>
  <p><fmt:formatDate value="${now}" type="both" dateStyle="long" timeStyle="long"/></p>

</jsp:attribute>
</my:pagetemplate>
