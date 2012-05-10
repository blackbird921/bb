<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="field" tagdir="/WEB-INF/tags/form/fields" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<script type="text/javascript">
    $(document).ready(function () {
        var validator = $("#title_fc_com_bb_domain_Customer_id").kendoValidator({
            messages:{
                required:"必填内容",
                passwordRepeated:"两次密码不一致"
            },
            rules:{
                passwordRepeated:function (input) {
                    return !input.is("[id=password_repeat_id]") ||
                            $("#password_repeat_id").val() == $("#password_id").val();
                }
            }
        }).data("kendoValidator");

        $("button").click(function () {
            if (!validator.validate()) {
                $(".status").text("请正确填写表格！").addClass("invalid");
                return false;
            }
        });
    });
</script>
<style type="text/css">
    #form-wrapper {
        border-radius: 10px 10px 10px 10px;
        border-style: solid;
        border-width: 1px;
        overflow: hidden;
        margin: 10px auto;
        padding: 20px 20px 0 20px;
        background-position: 0 -255px;
    }

    #example {
        max-width: 420px;
        margin-top: 30px;
        margin-left: auto;
        margin-right: auto;
        float: none;
    !important
    }
</style>

<%--<div id="main">--%>
<%--<h1 id="exampleTitle" class="k-content">--%>
<%--<span class="exampleIcon overviewIcon"></span>--%>
<%--注册--%>
<%--</h1>--%>
    <div id="example" class="k-content frame">
        <h1 class="content-title">
            <span class="exampleIcon overviewIcon"></span>
            1. 注册
        </h1>

        <div id="form-wrapper" class="k-header" style="height: 300px;">
            <form:create id="fc_com_bb_domain_Customer" modelAttribute="customer" multipart="true" path="/customers?create" render="${empty dependencies}" z="user-managed" label="下一步">
                <div class="errors" id="error_message">
                    <c:if test="${not empty usernameUniqueError}">
                        <spring:message code="error_usernameUniqueError" var="message_value" htmlEscape="false" />
                        <c:out value="${message_value}"/>
                        <br/>
                    </c:if>
                    <c:if test="${not empty emailUniqueError}">
                        <spring:message code="error_emailUniqueError" var="message_value" htmlEscape="false" />
                        <c:out value="${message_value}"/>
                        <br/>
                    </c:if>
                    <util:text id="usernameUniqueError" value="${usernameUniqueError}"/>
                    <util:text id="emailUniqueError" value="${emailUniqueError}"/>
                </div>
                <br/>
                <field:input field="username" id="c_com_bb_domain_Customer_username" max="30" min="1" required="true"/>
                <field:input field="password" id="c_com_bb_domain_Customer_password" max="15" min="4" required="true" type="password"/>
                <field:input field="password_repeat" id="password_repeat" label="确认密码" required="true"  type="password" disableFormBinding="true"/>
                <field:input field="email" id="c_com_bb_domain_Customer_email" max="30" min="5" required="true" type="email" validationMessageCode="field_invalid_email"/>
                <springform:hidden path="status" value="Trial"/>
            </form:create>
        </div>
    </div>
<%--</div>--%>

