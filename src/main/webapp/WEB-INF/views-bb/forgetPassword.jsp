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
                required:"必填内容"
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
        忘记密码？
    </h1>

    <div style="text-align: left; margin-top: 30px;">
        <form action="/mail/sendMail" method="GET" enctype="application/x-www-form-urlencoded">
            <ul style="list-style-type: none; margin: 0; padding: 0; !important" id="fc_com_bb_domain_Customer_ul">
                <div id="div_c_com_bb_domain_Customer_email_id">
                    <li><label for="email_id">电子邮箱:</label>
                        <input data-email-msg="电子邮箱格式无效" type="email" name="email" id="email_id" class="k-textbox"/><br/>
                        <script type="text/javascript">document.getElementById('email_id').setAttribute('required', 'true');</script>
                    </li>
                </div>
                <br/>
                <input type="hidden" value="forgotPwd" name="mailType"/>
                <li class="accept">
                    <button style="margin-left: 110px; margin-top: 10px; margin-right: 10px;" type="submit" class="k-button">
                        发送密码到我的邮箱
                    </button>
                    <span class="status"/></li>
            </ul>
        </form>
    </div>
</div>

