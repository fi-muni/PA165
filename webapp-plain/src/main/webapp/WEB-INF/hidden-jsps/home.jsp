<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%-- declare my own tags --%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%-- declare JSTL libraries --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- retrieve text for key "index.title" from ResourceBundle, set it as request attribute "title" --%>
<fmt:message var="title" key="index.title"/>

<%-- call my own tag defined in /WEB-INF/tags/pagetemplate.tag, provide title attribute --%>
<my:pagetemplate title="${title}">
<jsp:attribute name="body"><%-- provide page-fragment attribute to be rendered by the my:layout tag --%>
  <p><my:a href="/snoop.jsp"><fmt:message key="index.snoop.link"/></my:a></p>

  <!-- show Google login icon or user info -->
  <c:choose>
    <c:when test="${empty sessionScope['user']}">
      <p><my:a href="/google/login"><img src="${pageContext.request.contextPath}/google_login.png"></my:a></p>
    </c:when>
    <c:otherwise>
      <table class="basic">
        <tr>
          <th>user id</th>
          <td><c:out value="${user.id}"/></td>
        </tr>
        <tr>
          <th>email</th>
          <td><c:out value="${user.email}"/></td>
        </tr>
        <tr>
          <th>full name</th>
          <td><c:out value="${user.fullname}"/></td>
        </tr>
        <tr>
          <th>picture</th>
          <td><img src="${user.pictureURL}" /></td>
        </tr>
      </table>
    </c:otherwise>
  </c:choose>

</jsp:attribute>
</my:pagetemplate>
