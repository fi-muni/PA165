<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ attribute name="href" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value='${href}' var="url" scope="page"/>
<a href="<c:out value='${url}'/>"><jsp:doBody/></a>