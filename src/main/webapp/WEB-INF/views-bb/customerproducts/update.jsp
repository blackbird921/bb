<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="field" tagdir="/WEB-INF/tags/form/fields" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<script type="text/javascript">
    $(document).ready(function () {
        $(document).ready(function () {
            $(".grid").kendoGrid({
                groupable:false,
                scrollable:false,
                sortable:false,
                pageable:false
            });

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
        修改计划
    </h1>

    <div id="example" class="k-content">
        <h4>当前出勤计划:</h4>
        <table class="grid center-text">
            <thead>
            <tr>
                <th class="center-text" data-field="title" width="50%">出勤承诺天数</th>
                <th class="center-text" data-field="year">缺勤每天罚金</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${customerproduct.productCommit.commits}天</td>
                <td>${customerproduct.productStake.stakes}元</td>
            </tr>
            </tbody>
        </table>

        <h4>将来出勤计划:</h4>
        <div id="form-wrapper" class="k-header" style="height: 200px;">
            <form:update id="fu_com_bb_domain_CustomerProduct" modelAttribute="customerproduct" path="/customerproducts" versionField="Version" z="tXGbjniBAOW9I55SMK7PCIxXFe4=">
                <field:select field="productCommit" id="c_com_bb_domain_CustomerProduct_productCommit" itemValue="commits" items="${productcommits}" path="/productcommits" required="true"/>
                <field:select field="productStake" id="c_com_bb_domain_CustomerProduct_productStake" itemValue="stakes" items="${productstakes}" path="/productstakes" required="true"/>
                <li>
                    <spring:message code="label_${fn:toLowerCase(fn:substringAfter('c_com_bb_domain_CustomerProduct_startDate','_'))}" htmlEscape="false" var="label"/>
                    <label for="c_com_bb_domain_CustomerProduct_startDate">
                        <c:out value="${label}"/>:</label>
                    <input id="c_com_bb_domain_CustomerProduct_startDate" name="startDate" type="text" value="" readonly="" class="k-textbox" required/>
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
            </form:update>
        </div>
    </div>
</div>


