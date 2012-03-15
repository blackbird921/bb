<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="taglib.jsp" %>
<!doctype html public "-//w3c//dtd xhtml 1.0 transitional//en">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html;Charset='UTF-8'"/>
    <spring:message code="application_name" var="app_name" htmlEscape="false"/>
    <title><spring:message code="welcome_h3" arguments="${app_name}"/></title>
    <util:load-scripts/>

</head>
<body class="tundra spring">
<div id="exceptFooterWrap">
    <div id="exceptFooter">
        <tiles:insertAttribute name="header" ignore="true"/>
        <div id="navmainWrap" class="floatWrap centerWrap hasNavigation container_12">
            <tiles:insertAttribute name="menu" ignore="true"/>
            <tiles:insertAttribute name="body"/>
        </div>
    </div>
</div>
<tiles:insertAttribute name="footer" ignore="true"/>
</body>
</html>
