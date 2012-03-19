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
        var validator = $("form").kendoValidator({
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
        2. 设定出勤计划
    </h1>

    <div id="form-wrapper" class="k-header" style="height: 300px;">
        <%--<form:create id="fc_com_bb_domain_Customer" modelAttribute="customer" multipart="true" path="/customers?create" render="${empty dependencies}" z="user-managed" label="下一步">--%>
            <%--<div class="errors" id="error_message">--%>
                <%--<util:text id="usernameUniqueError" value="${usernameUniqueError}"/>--%>
                <%--<util:text id="emailUniqueError" value="${emailUniqueError}"/>--%>
            <%--</div>--%>
            <%--<br/>--%>
            <%--<field:input field="username" id="c_com_bb_domain_Customer_username" max="30" min="1" required="true"/>--%>
            <%--<field:input field="password" id="c_com_bb_domain_Customer_password" max="15" min="4" required="true" type="password"/>--%>
            <%--<field:input field="password_repeat" id="password_repeat" label="确认密码" required="true"  type="password" disableFormBinding="true"/>--%>
            <%--<field:input field="email" id="c_com_bb_domain_Customer_email" max="30" min="5" required="true" type="email" validationMessageCode="field_invalid_email"/>--%>
            <%--<springform:hidden path="status" value="Trial"/>--%>
        <%--</form:create>--%>
            <form:create id="fc_com_bb_domain_CustomerProduct" modelAttribute="futurecustomerproduct" path="/customerproducts?create" render="${empty dependencies}" label="下一步">
                <input id="customer_id" name="customer" value="${futurecustomerproduct.customer.id}" type="hidden"/>
                <field:select field="productCommit" id="c_com_bb_domain_CustomerProduct_productCommit" itemValue="id" itemLabel="commits" items="${productcommits}" path="/productcommits" required="true"/>
                <field:select field="productStake" id="c_com_bb_domain_CustomerProduct_productStake" itemValue="id" itemLabel="stakes" items="${productstakes}" path="/productstakes" required="true"/>
                <li>
                    <spring:message code="label_${fn:toLowerCase(fn:substringAfter('c_com_bb_domain_CustomerProduct_startDate','_'))}" htmlEscape="false" var="label"/>
                    <label for="c_com_bb_domain_CustomerProduct_startDate">
                        <c:out value="${label}"/>:</label>
                    <c:if test="${futurecustomerproduct.showStartDate}">
                        <fmt:formatDate pattern='yyyy-MM-dd' value='${futurecustomerproduct.startDate}' var="futureStartDate"/>
                    </c:if>
                    <input id="c_com_bb_domain_CustomerProduct_startDate" name="startDate" type="text" value="${futureStartDate}" readonly="" class="k-textbox" required/>
                </li>
                <script src="/resources/scripts/jquery-ui/development-bundle/ui/i18n/jquery.ui.datepicker-zh-CN.js" type="text/javascript">
                    <!--jquery ui-->
                </script>
                <script>
                    $(function () {
                        $("#c_com_bb_domain_CustomerProduct_startDate").datepicker({
                            dateFormat:"yy-mm-dd",
                            numberOfMonths:1,
                            showButtonPanel:true,
                            minDate:-0, maxDate:'+12M',
                            beforeShowDay:function (date) {
                                return [date.getDay() == 1, ""]
                            }
                        });
                    });
                </script>
            </form:create>
    </div>
</div>
<%--</div>--%>

