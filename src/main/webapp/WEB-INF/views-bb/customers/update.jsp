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
        $(".grid").kendoGrid({
            groupable:false,
            scrollable:false,
            sortable:false,
            pageable:false
        });


        var validator = $("#title_fu_com_bb_domain_CustomerProduct_id").kendoValidator({
            messages:{
                required:"必填内容"
            }
        }).data("kendoValidator");

        $("button").click(function () {
            if (!validator.validate()) {
//                $(".status").text("请正确填写表格！").addClass("invalid");
                return false;
            }
        });
    });
</script>


<div id="main">
    <h1 id="exampleTitle" class="k-content">
        <span class="exampleIcon overviewIcon"></span>
        个人信息
    </h1>

    <div id="example" class="k-content">

        <div id="form-wrapper" class="k-header" style="height: 700px; width:400px;">
            <form:update id="fu_com_bb_domain_Customer" modelAttribute="customer" multipart="true" path="/customers/update" versionField="Version" z="user-managed" label="更新">
                <div class="errors" id="error_message">
                    <util:text id="usernameUniqueError" value="${usernameUniqueError}"/>
                    <util:text id="emailUniqueError" value="${emailUniqueError}"/>
                </div>
                <util:avatar big="false" customerId="${customer.id}" hasUploadedAvatar="${customer.hasAvatar}" id="my_avartar" showUploadButton="true"/>
                <field:input field="username" id="c_com_bb_domain_Customer_username" max="30" min="1" required="true" z="g8lc5GP2QhoIkXAhb5qwgRfxeq4="/>
                <field:input field="password" id="c_com_bb_domain_Customer_password" max="15" min="4" required="true" z="m85pGvbzWxsKJt71sJA4uaIyqM0="/>
                <field:input field="email" id="c_com_bb_domain_Customer_email" max="30" min="5" required="true" validationMessageCode="field_invalid_email" z="yaUEIVCqoUJKOTy2AjZUw4e48bE="/>
                <c:choose>
                    <c:when test="${isAdmin==true}">
                        <field:select field="status" id="c_com_bb_domain_Customer_status" items="${customerstatuses}" path="customerstatuses" required="true" z="UV0g0yUASgZWX7/Fuu1mEyfCWhE="/>
                        <field:select field="customerRole" id="c_com_bb_domain_Customer_customerRole" items="${customerroles}" path="customerroles" z="MSKPoQdGvqyHKEe6iwiuVAOBdbg="/>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" id="c_com_bb_domain_Customer_status" name="status" value="${customer.status}"/>
                    </c:otherwise>
                </c:choose>
                <field:input field="name" id="c_com_bb_domain_Customer_name" max="30" z="Bz3Vq0TFu8udneCdv+7pRTWKGYo="/>
                <field:input field="address" id="c_com_bb_domain_Customer_address" z="2vg4PmmT+M5SrTp5f9TZ7LcyscU="/>
                <field:input field="city" id="c_com_bb_domain_Customer_city" max="30" z="o53VstJDEAwMjEWDD1UuH0J05Ko="/>
                <field:input field="phone" id="c_com_bb_domain_Customer_phone" max="30" z="S/m0ZhJttvjKHJNpnqdPouHr9KM="/>
                <field:select field="sex" id="c_com_bb_domain_Customer_sex" items="${refsexes}" itemValue="id" path="refsexes" render="true" z="user-managed"/>
                <field:input field="bio" id="c_com_bb_domain_Customer_bio" z="PXxLGNN35vTh9yXiB23kXDf44ig="/>
                <field:input field="registrationDate" id="c_com_bb_domain_Customer_registrationDate" z="PXxLGNN35vTh9yXiB23kXDf44ig=" disabled="true"/>
                <%--<field:display date="true" dateTimePattern="${customer_registrationdate_date_format}" field="registrationDate" id="c_com_bb_domain_Customer_registrationDate" object="${customer}" z="user-managed"/>--%>
                <%--<field:datetime dateTimePattern="${customer_birthday_date_format}" field="birthday" id="c_com_bb_domain_Customer_birthday" past="true" z="D1Wx2MGX6FHU72/u9LcGJ85ysb8="/>--%>
                <li>
                    <spring:message code="label_${fn:toLowerCase(fn:substringAfter('c_com_bb_domain_Customer_birthday','_'))}" htmlEscape="false" var="label"/>
                    <label for="c_com_bb_domain_Customer_birthday">
                        <c:out value="${label}"/>:</label>
                    <fmt:formatDate pattern='yyyy-MM-dd' value='${customer.birthday}' var="formatBirthday"/>
                    <input id="c_com_bb_domain_Customer_birthday" name="birthday" type="text" value="${formatBirthday}" readonly="" class="k-textbox" required/>
                </li>
                <script src="/resources/scripts/jquery-ui/development-bundle/ui/i18n/jquery.ui.datepicker-zh-CN.js" type="text/javascript">
                    <!--jquery ui-->
                </script>
                <script>
                    $(function () {
                        $("#c_com_bb_domain_Customer_birthday").datepicker({
                            dateFormat:"yy-mm-dd",
                            numberOfMonths:3,
                            maxDate:'-6Y'
                        });
                    });
                </script>
                <div style="display: none;">
                        <%--<field:datetime dateTimePattern="${customer_disablestartdate_date_format}" field="disableStartDate" id="c_com_bb_domain_Customer_disableStartDate" z="Ap61W6JB2kxJ+35cWqIsDWcCx50="/>--%>
                    <li>
                        <spring:message code="label_${fn:toLowerCase(fn:substringAfter('c_com_bb_domain_Customer_disableStartDate','_'))}" htmlEscape="false" var="label"/>
                        <label for="c_com_bb_domain_Customer_disableStartDate">
                            <c:out value="${label}"/>:</label>
                        <fmt:formatDate pattern='yyyy-MM-dd' value='${customer.disableStartDate}' var="formatStartDate"/>
                        <input id="c_com_bb_domain_Customer_disableStartDate" name="disableStartDate" type="text" value="${formatStartDate}" readonly="" class="k-textbox" required/>

                    </li>
                    <script src="/resources/scripts/jquery-ui/development-bundle/ui/i18n/jquery.ui.datepicker-zh-CN.js" type="text/javascript">
                        <!--jquery ui-->
                    </script>
                    <script>
                        $(function () {
                            $("#c_com_bb_domain_Customer_disableStartDate").datepicker({
                                dateFormat:"yy-mm-dd",
                                numberOfMonths:1,
                                minDate:-0
                            });
                        });
                    </script>

                        <%--<field:datetime dateTimePattern="${customer_disableenddate_date_format}" field="disableEndDate" id="c_com_bb_domain_Customer_disableEndDate" z="yZchis6yqg84AB8gPYVLa3IdvEg="/>--%>
                    <li>
                        <spring:message code="label_${fn:toLowerCase(fn:substringAfter('c_com_bb_domain_Customer_disableEndDate','_'))}" htmlEscape="false" var="label"/>
                        <label for="c_com_bb_domain_Customer_disableEndDate">
                            <c:out value="${label}"/>:</label>
                        <fmt:formatDate pattern='yyyy-MM-dd' value='${customer.disableEndDate}' var="formatEndDate"/>
                        <input id="c_com_bb_domain_Customer_disableEndDate" name="disableEndDate" type="text" value="${formatEndDate}" readonly="" class="k-textbox" required/>
                    </li>
                    <script src="/resources/scripts/jquery-ui/development-bundle/ui/i18n/jquery.ui.datepicker-zh-CN.js" type="text/javascript">
                        <!--jquery ui-->
                    </script>
                    <script>
                        $(function () {
                            $("#c_com_bb_domain_Customer_disableEndDate").datepicker({
                                dateFormat:"yy-mm-dd",
                                numberOfMonths:1,
                                minDate:+1
                            });
                        });
                    </script>


                    <%--<field:input field="disableReason" id="c_com_bb_domain_Customer_disableReason" z="C7+VkdMG9tY212iWBEGul6RpM40="/>--%>
                </div>
                <%--<field:input disabled="true" field="avatar" id="c_com_bb_domain_Customer_avatar" render="false" z="user-managed"/>--%>
                <%--<field:checkbox field="hasAvatar" id="c_com_bb_domain_Customer_hasAvatar" render="false" z="user-managed"/>--%>
                <%--<springform:hidden id="my_com_bb_domain_Customer_hasAvatar" path="hasAvatar"/>--%>
            </form:update>
        </div>
    </div>
</div>


