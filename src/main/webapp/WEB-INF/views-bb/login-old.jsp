<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="field" tagdir="/WEB-INF/tags/form/fields" %>
<%@ taglib prefix="util" tagdir="/WEB-INF/tags/util" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div>
  <spring:message code="security_login_title" var="title" htmlEscape="false" />
  <util:panel id="title" title="${title}">
    <c:if test="${not empty param.login_error}">
      <div class="errors">
        <p>
          <spring:message code="security_login_unsuccessful" />
          <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
          .
        </p>
      </div>
    </c:if>
    <c:if test="${empty param.login_error}">
      <p>
        <spring:message code="security_login_message" />
      </p>
    </c:if>
    <spring:url value="/resources/j_spring_security_check" var="form_url" />
    <form name="f" action="${fn:escapeXml(form_url)}" method="POST">
      <div>
        <label for="j_username">
          <spring:message code="security_login_form_name" />
        </label>
        <input id="j_username" type='text' name='j_username' style="width:150px" />
        <spring:message code="security_login_form_name_message" var="name_msg" htmlEscape="false" />
        <script type="text/javascript">
          <c:set var="sec_name_msg">
            <spring:escapeBody javaScriptEscape="true">${name_msg}</spring:escapeBody>
          </c:set>
          Spring.addDecoration(new Spring.ElementDecoration({elementId : "j_username", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {promptMessage: "${sec_name_msg}", required : true}}));
        </script>
      </div>
      <br />
      <div>
        <label for="j_password">
          <spring:message code="security_login_form_password" />
        </label>
        <input id="j_password" type='password' name='j_password' style="width:150px" />
        <spring:message code="security_login_form_password_message" var="pwd_msg" htmlEscape="false" />
        <script type="text/javascript">
          <c:set var="sec_pwd_msg">
            <spring:escapeBody javaScriptEscape="true">${pwd_msg}</spring:escapeBody>
          </c:set>
          Spring.addDecoration(new Spring.ElementDecoration({elementId : "j_password", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {promptMessage: "${sec_pwd_msg}", required : true}}));
        </script>
      </div>
      <br />
      <div class="submit">
        <script type="text/javascript">Spring.addDecoration(new Spring.ValidateAllDecoration({elementId:'proceed', event:'onclick'}));</script>
        <spring:message code="button_submit" var="submit_label" htmlEscape="false" />
        <input id="proceed" type="submit" value="${fn:escapeXml(submit_label)}" />
        <spring:message code="button_reset" var="reset_label" htmlEscape="false" />
        <input id="reset" type="reset" value="${fn:escapeXml(reset_label)}" />
      </div>
    </form>
  </util:panel>
</div>

