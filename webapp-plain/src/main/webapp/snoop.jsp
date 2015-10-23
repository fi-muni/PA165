<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.TreeMap" %>
<%@ page session="true" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
 <title> Snoop </title>
</head>
<body>

<h2>Request and server info</h2>

 <table border="1" bgcolor="#e0e0e0">
 <tr><th colspan="2" align="left">Headers:</th></tr>
 <c:forEach items="${header}" var="h" >
   <tr>
    <td><c:out value="${h.key}"/></td>
    <td><c:out value="${h.value}"/></td>
   </tr>
 </c:forEach>
 </table>

 <table border="1" bgcolor="#e0e0e0">
 <tr><th colspan="2" align="left">Request Params:</th></tr>
 <c:forEach items="${param}" var="p" >
   <tr>
    <td><c:out value="${p.key}"/></td>
    <td><c:out value="${p.value}"/></td>
   </tr>
 </c:forEach>
 </table>


<hr width="90%">

<table border="1" bgcolor="yellow">
    <tr>
        <th colspan="2">pageContext.servletContext</th>
    </tr>
    <tr>
        <td> majorVersion.minorVersion</td>
        <td> ${pageContext.servletContext.majorVersion}.${pageContext.servletContext.minorVersion}</td>
    </tr>
    <tr>
        <td> effectiveVersion</td>
        <td> ${pageContext.servletContext.effectiveMajorVersion}.${pageContext.servletContext.effectiveMinorVersion}</td>
    </tr>
    <tr>
        <td> serverInfo</td>
        <td> ${pageContext.servletContext.serverInfo} </td>
    </tr>
    <tr>
        <td> servletContextName</td>
        <td> ${pageContext.servletContext.servletContextName} </td>
    </tr>
    <tr>
        <td>effectiveSessionTrackingModes</td>
        <td> ${pageContext.servletContext.effectiveSessionTrackingModes}</td>
    </tr>
    <tr><th colspan="2" align="left">servlets</th></tr>
    <c:forEach items="${pageContext.servletContext.servletRegistrations}" var="me">
        <tr>
            <td>servlet "${me.key}"</td>
            <td> ${me.value.className} ${me.value.mappings}</td>
        </tr>
    </c:forEach>

    <tr><th colspan="2" align="left">initParam (servletContext/application)</th></tr>
    <c:forEach items="${initParam}" var="p" >
        <tr>
            <td>${p.key}</td>
            <td>${p.value}</td>
        </tr>
    </c:forEach>
</table>
<table border="1" bgcolor="orange">
    <tr>
        <th colspan="2">javax.servlet.http.HttpServletRequest</th>
    </tr>
    <tr>
        <td> pageContext.request.authType</td>
        <td> ${pageContext.request.authType} </td>
    </tr>
    <tr>
        <td> pageContext.request.contextPath</td>
        <td> ${pageContext.request.contextPath} </td>
    </tr>
    <tr>
        <td> pageContext.request.method</td>
        <td> ${pageContext.request.method} </td>
    </tr>
    <tr>
        <td> pageContext.request.pathInfo</td>
        <td> ${pageContext.request.pathInfo} </td>
    </tr>
    <tr>
        <td> pageContext.request.pathTranslated</td>
        <td> ${pageContext.request.pathTranslated} </td>
    </tr>
    <tr>
        <td> pageContext.request.queryString</td>
        <td> ${pageContext.request.queryString} </td>
    </tr>
    <tr>
        <td> pageContext.request.remoteUser</td>
        <td> ${pageContext.request.remoteUser} </td>
    </tr>
    <tr>
        <td> pageContext.request.requestedSessionId</td>
        <td> ${pageContext.request.requestedSessionId} </td>
    </tr>
    <tr>
        <td> pageContext.request.requestURI</td>
        <td> ${pageContext.request.requestURI} </td>
    </tr>
    <tr>
        <td> pageContext.request.requestURL</td>
        <td> ${pageContext.request.requestURL} </td>
    </tr>
    <tr>
        <td> pageContext.request.servletPath</td>
        <td> ${pageContext.request.servletPath} </td>
    </tr>
    <tr>
        <td> pageContext.request.userPrincipal</td>
        <td> ${pageContext.request.userPrincipal} </td>
    </tr>

    <tr>
        <th colspan="2">javax.servlet.ServletRequest</th>
    </tr>
    <tr>
        <td> pageContext.request.characterEncoding</td>
        <td> ${pageContext.request.characterEncoding} </td>
    </tr>
    <tr>
        <td> pageContext.request.contentLength</td>
        <td> ${pageContext.request.contentLength} </td>
    </tr>
    <tr>
        <td> pageContext.request.contentType</td>
        <td> ${pageContext.request.contentType} </td>
    </tr>
    <tr>
        <td> pageContext.request.localAddr</td>
        <td> ${pageContext.request.localAddr} </td>
    </tr>
    <tr>
        <td> pageContext.request.localName</td>
        <td> ${pageContext.request.localName} </td>
    </tr>
    <tr>
        <td> pageContext.request.localPort</td>
        <td> ${pageContext.request.localPort} </td>
    </tr>
    <tr>
        <td> pageContext.request.locale</td>
        <td> ${pageContext.request.locale} </td>
    </tr>
    <tr>
        <td> pageContext.request.protocol</td>
        <td> ${pageContext.request.protocol} </td>
    </tr>
    <tr>
        <td> pageContext.request.remoteAddr</td>
        <td> ${pageContext.request.remoteAddr} </td>
    </tr>
    <tr>
        <td> pageContext.request.remoteHost</td>
        <td> ${pageContext.request.remoteHost} </td>
    </tr>
    <tr>
        <td> pageContext.request.remotePort</td>
        <td> ${pageContext.request.remotePort} </td>
    </tr>
    <tr>
        <td> pageContext.request.scheme</td>
        <td> ${pageContext.request.scheme} </td>
    </tr>
    <tr>
        <td> pageContext.request.serverName</td>
        <td> ${pageContext.request.serverName} </td>
    </tr>
    <tr>
        <td> pageContext.request.serverPort</td>
        <td> ${pageContext.request.serverPort} </td>
    </tr>
    <tr>
        <td> pageContext.request.secure</td>
        <td> ${pageContext.request.secure} </td>
    </tr>
    <tr>
        <th colspan="2">javax.servlet.http.HttpSession</th>
    </tr>
    <tr>
        <td> pageContext.request.session.id</td>
        <td> ${pageContext.request.session.id} </td>
    </tr>
    <tr>
        <td> pageContext.request.session.lastAccessedTime</td>
        <td> ${pageContext.request.session.lastAccessedTime} </td>
    </tr>
    <tr>
        <td> pageContext.request.session.maxInactiveInterval</td>
        <td> ${pageContext.request.session.maxInactiveInterval} </td>
    </tr>
    <tr>
        <th colspan="2">other</th>
    </tr>
    <tr>
        <td> pageContext.page.servletInfo</td>
        <td> ${pageContext.page.servletInfo} </td>
    </tr>
    <tr>
        <td> pageContext.servletConfig.servletName</td>
        <td> ${pageContext.servletConfig.servletName} </td>
    </tr>
    <tr>
        <td> javax.servlet.jsp.JspFactory</td>
        <td> <%=javax.servlet.jsp.JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion()%></td>
    </tr>
</table>

 <hr width="90%">

 <table border="1" bgcolor="cyan">
 <tr><th colspan="2" align="left">page scope attributes:</th></tr>
 <c:forEach items="${pageScope}" var="a" >
   <tr>
    <td><c:out value="${a.key}"/></td>
    <td><c:out value="${a.value}"/></td>
   </tr>
 </c:forEach>
 <tr><th colspan="2" align="left">request scope attributes:</th></tr>
 <c:forEach items="${requestScope}" var="a" >
   <tr>
    <td><c:out value="${a.key}"/></td>
    <td><c:out value="${a.value}"/></td>
   </tr>
 </c:forEach>
 <tr><th colspan="2" align="left">session scope attributes:</th></tr>
 <c:forEach items="${sessionScope}" var="a" >
   <tr>
    <td><c:out value="${a.key}"/></td>
    <td><c:out value="${a.value}"/></td>
   </tr>
 </c:forEach>
 <tr><th colspan="2" align="left">application scope attributes:</th></tr>
 <c:forEach items="${applicationScope}" var="a" >
   <tr>
    <td><c:out value="${a.key}"/></td>
    <td><c:out value="${fn:substring(a.value,0,120)}"/></td>
   </tr>
 </c:forEach>
 </table>




<hr width="90%">

  <table border="1" bgcolor="cornsilk">
   <tr>
    <th colspan="2">JVM</th>
   </tr>
      <tr>
          <th>Java</th>
          <td><%=System.getProperty("java.version")%> <%=System.getProperty("java.vendor")%></td>
      </tr>
      <tr>
          <th>JRE</th>
          <td><%=System.getProperty("java.runtime.version")%> <%=System.getProperty("java.runtime.name")%></td>
      </tr>
      <tr>
          <th>OS</th>
          <td><%=System.getProperty("os.name")%> <%=System.getProperty("os.version")%> (<%=System.getProperty("os.arch")%>)</td>
      </tr>
      <tr>
          <th>JVM</th>
          <td><%=System.getProperty("java.vm.version")%> <%=System.getProperty("java.vm.vendor")%>
              <%=System.getProperty("java.vm.name")%> <%=System.getProperty("java.vm.info")%>
          </td>
      </tr>
      <tr>
          <th>Locale.getDefault()</th>
          <td><%=Locale.getDefault().getDisplayName(Locale.US)%></td>
      </tr>
   <tr>
    <th colspan="2">System properties</th>
   </tr>
      <%
          //noinspection unchecked
          for(Map.Entry e:(Set <Map.Entry<String,String>>)new TreeMap(System.getProperties()).entrySet()) {
              String s = (String)e.getValue();
              if(s!=null&&s.length()>90) s = s.substring(0,90)+" ...";
            %>
              <tr>
                  <th style="font-size: smaller; text-align:left"><%=e.getKey()%></th>
                  <td style="font-size: smaller;"><%=s%></td>
              </tr>
            <%
          }
      %>
  </table>

 <h2>application attribute org.apache.tomcat.util.scan.MergedWebXml</h2>
 <pre style="border: 1px solid black; background-color: aliceblue;"><c:out value="${applicationScope['org.apache.tomcat.util.scan.MergedWebXml']}"/></pre>
</body>
</html>
