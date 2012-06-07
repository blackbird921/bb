<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!--header.jsp-->
<div id="header">
    <div class="floatWrap centerWrap">
        <h1><a id="logo" href="">Kendo</a></h1>
        <ul>
            <li><a href="/">首页</a></li>
            <sec:authorize access="isAuthenticated()">
                <li class="active"><a href="/customerproducts/show">我的空间</a></li>
            </sec:authorize>
            <sec:authorize access="isAnonymous()">
                <%--<li class="active"><a href="/intro">了解动则赢</a></li>--%>
            </sec:authorize>
            <li><a href="/faqs">常见问题</a></li>
            <li><a href="/about">关于我们</a></li>

            <sec:authorize access="isAnonymous()">
                <li><a href="/register/form">注册</a></li>
                <li><a id="login" href="/login">登录</a></li>
            </sec:authorize>

            <sec:authorize access="isAuthenticated()">
                <li><a id="logout" href="/resources/j_spring_security_logout">退出</a></li>
            </sec:authorize>
            <li><a id="download" href="">下载手机应用</a></li>
        </ul>
    </div>
</div>



