<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="field" tagdir="/WEB-INF/tags/form/fields" %>
<%@ taglib prefix="util" tagdir="/WEB-INF/tags/util" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="login-window-wrapper" style="margin-left: 150px;">
    <div id="login-window" style="display: block;">

        <form id="customer_login" action="/" method="POST" enctype="application/x-www-form-urlencoded">
            <ul style="list-style-type: none;!important">
                <li><label for="username_id">用户名/邮箱:</label>
                    <input id="username_id" class="k-textbox" name="username" type="text" value="" required="true"/><br/>
                </li>
                <br/>
                <li><label for="password_id">密码:</label>
                    <input id="password_id" class="k-textbox" name="password" type="password" value="" required="true"/>
                    <a href="">忘记密码？</a>
                    <br/>
                </li>
                <br/>
                <li class="accept">
                    <button style="margin-left: 110px; margin-top: 10px; margin-right: 10px;" type="submit" class="k-button">
                        登录
                    </button>
                    <span class="status"/></li>
            </ul>
        </form>
    </div>
</div>

<script>
    var loginWindow = $("#login-window"),
            loginWindowWrapper = $("#login-window-wrapper"),
            loginButton = $("#login");


    loginButton.bind("click", function () {
        loginWindow.show();
        loginWindow.data("kendoWindow").open();
    });

    if (!loginWindow.data("kendoWindow")) {
        loginWindow.kendoWindow({
            width:"500px",
            actions:["Close"],
            modal:true,
            title:"登录"

        });
    }


</script>