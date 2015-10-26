<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- workaround, cannot call from EL and cannot use scriptlet from JSP fragment attribute --%>
<%request.setAttribute("admin", request.isUserInRole("administrator")); %>

<fmt:message var="title" key="podoli.title"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">

  <p><fmt:message key="podoli.text"/> </p>

    <table class="basic">
      <tr>
        <td>authType</td>
        <td><c:out value="${pageContext.request.authType}"/></td>
      </tr>
      <tr>
        <td>remoteUser</td>
        <td><c:out value="${pageContext.request.remoteUser}"/></td>
      </tr>
      <tr>
        <td>userPrincipal.name</td>
        <td><c:out value="${pageContext.request.userPrincipal.name}"/></td>
      </tr>
      <tr>
        <td>request.isUserInRole("administrator"))</td>
        <td>${admin}</td>
      </tr>
    </table>

  <form method="post" action="<c:url value='/podoli'/>">
    <input type="submit" value="Logout">
  </form>

</jsp:attribute>
</my:pagetemplate>
