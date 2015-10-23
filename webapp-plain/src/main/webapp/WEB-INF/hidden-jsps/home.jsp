<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
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
  <p><my:a href="/praha.jsp"><fmt:message key="index.praha.link"/></my:a></p>
</jsp:attribute>
</my:pagetemplate>
