<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="field" tagdir="/WEB-INF/tags/form/fields" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style type="text/css">
    #example {
        max-width: 420px;
        margin-top: 30px;
        margin-left: auto;
        margin-right: auto;
        float: none;
    !important
    }
</style>
<div id="example" class="k-content frame">
    <h1 class="content-title">
        <span class="exampleIcon overviewIcon"></span>
        发送密码到邮箱
    </h1>

    <div style="text-align: left; margin-top: 30px; margin-left: 20px;">
        <c:if test="${sendMailResult==true}">
            你的密码已发送到<span style="font-weight: bold; margin: 0 4px;"><c:out value="${email}"/></span>。
           <p>请登录你的邮箱获取密码后，重新登录。</p>
            <button style="margin-top: 10px; margin-right: 10px;" id="button-return" class="k-button">
                登录
            </button>
            <script type="text/javascript">
                $("#button-return").click(function (e) {
                    window.location = "/login";
                });
            </script>
        </c:if>
        <c:if test="${sendMailResult==false}">
            我们无法发送你的密码到邮箱 <span style="font-weight: bold; margin-left: 4px;"><c:out value="${email}"/></span>。
            <br>
            <br>
            请确认你已输入正确的邮箱地址，并确认你的邮箱可以正常接收邮件。
           <p>

           </p>
            <button style="margin-top: 10px; margin-right: 10px;" id="button-return" class="k-button">
                返回
            </button>
            <script type="text/javascript">
                $("#button-return").click(function (e) {
                    window.location = "/forgetPassword";
                });
            </script>
        </c:if>
    </div>
</div>

