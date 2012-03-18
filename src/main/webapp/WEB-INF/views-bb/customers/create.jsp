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
</style>

<div id="main">
    <h1 id="exampleTitle" class="k-content">
        <span class="exampleIcon overviewIcon"></span>
        注册
    </h1>

    <div id="example" class="k-content">

        <div id="form-wrapper" class="k-header" style="height: 300px;">
            <form:create id="fc_com_bb_domain_Customer" modelAttribute="customer" multipart="true" path="/customers" render="${empty dependencies}" z="user-managed">
                <div class="errors" id="error_message">
                    <util:text id="usernameUniqueError" value="${usernameUniqueError}"/>
                    <util:text id="emailUniqueError" value="${emailUniqueError}"/>
                </div>
                <br/>
                <field:input field="username" id="c_com_bb_domain_Customer_username" max="30" min="1" required="true"/>
                <field:input field="password" id="c_com_bb_domain_Customer_password" max="15" min="4" required="true" type="password"/>
                <field:input field="password_repeat" id="password_repeat" label="确认密码" required="true" disableFormBinding="true"/>
                <field:input field="email" id="c_com_bb_domain_Customer_email" max="30" min="5" required="true" type="email" validationMessageCode="field_invalid_email"/>
            </form:create>
        </div>
    </div>
</div>

