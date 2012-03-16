<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--<%@ taglib prefix="jsp" uri="http://java.sun.com/JSP/Page" %>--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="util" tagdir="/WEB-INF/tags/util" %>


<div>
  <spring:message var="title" code="error_uncaughtexception_title" htmlEscape="false" />
  <util:panel id="title" title="${title}">
    <h2>${fn:escapeXml(title)}</h2>
    <p>
      <spring:message code="error_uncaughtexception_problemdescription" />
    </p>
    <c:if test="${not empty exception}">
      <p>
        <h4>
          <spring:message code="exception_details" />
        </h4>
        <spring:message var="message" code="exception_message" htmlEscape="false" />
        <util:panel id="_message" title="${message}" openPane="false">
          <c:out value="${exception.localizedMessage}" />
        </util:panel>
        <spring:message var="stacktrace" code="exception_stacktrace" htmlEscape="false" />
        <util:panel id="_exception" title="${stacktrace}" openPane="false">
          <c:forEach items="${exception.stackTrace}" var="trace">
            <c:out value="${trace}" />
            <br />
          </c:forEach>
        </util:panel>
      </p>
    </c:if>
  </util:panel>
</div>