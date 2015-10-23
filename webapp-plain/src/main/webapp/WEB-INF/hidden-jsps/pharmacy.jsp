<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:message var="title" key="pharmacy.title"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">

<form method="post" action="<c:url value='/pharmacy'/>">

    <table>
        <tr>
            <td><label for="drugname"><fmt:message key="drug.name"/>:</label></td>
            <td><input type="text" id="drugname" name="name" size="30"></td>
        </tr>
        <tr>
            <td><label for="drugamount"><fmt:message key="drug.amount"/>:</label></td>
            <td><input type="text" id="drugamount" name="amount"></td>
        </tr>
        <tr>
            <td><label for="drugvendor"><fmt:message key="drug.vendor"/>:</label></td>
            <td><input type="text" id="drugvendor" name="vendor"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" ></td>
        </tr>
    </table>


</form>

<table class="basic">
    <tr>
        <th><fmt:message key="drug.name"/></th>
        <th><fmt:message key="drug.amount"/></th>
        <th><fmt:message key="drug.vendor"/></th>
    </tr>
  <c:forEach items="${drugs}" var="d">
    <tr>
      <td><c:out value="${d.name}"/></td>
      <td><c:out value="${d.amount}"/></td>
      <td><c:out value="${d.vendor}"/></td>
    </tr>
  </c:forEach>
</table>

</jsp:attribute>
</my:pagetemplate>
