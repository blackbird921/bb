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

<div id="example" class="k-content frame">
    <h1 class="content-title">
        <span class="exampleIcon overviewIcon"></span>
        4. 获取健身卡
    </h1>

    <div id="form-wrapper" class="k-header" style="height: 300px;">
        <form:create id="fc_com_bb_domain_CustomerCard" modelAttribute="customerCard" path="/customercards" render="${empty dependencies}" label="完成">
            <input id="customer_id" name="customer" value="${customerCard.customer.id}" type="hidden"/>
            <input id="isWizard" name="wizard" value="true" type="hidden"/>
            <input type="hidden" name="issuedDate" value="${issuedDate}"/>
            <input type="hidden" name="status" value="未使用"/>

            <field:select field="card" id="c_com_bb_domain_CustomerCard_card" itemValue="id" items="${cards}" path="/cards" required="true" z="Z7ebk5OBKmaDf94Y/sP9q4nV/J8="/>
            <div style="margin: 5px 57px;">${description}</div>
        </form:create>
    </div>
</div>


