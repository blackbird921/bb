<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="field" tagdir="/WEB-INF/tags/form/fields" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

    });
</script>

<div id="main">
    <h1 id="exampleTitle" class="k-content">
        <span class="exampleIcon overviewIcon"></span>
        修改计划
    </h1>

    <div id="exampleWrap" style="visibility: visible; ">
        <div id="example" class="k-content">
            <form:update id="fu_com_bb_domain_CustomerProduct" modelAttribute="customerProduct" path="/customerproducts" versionField="Version" z="tXGbjniBAOW9I55SMK7PCIxXFe4=">
                <field:select field="productCommit" id="c_com_bb_domain_CustomerProduct_productCommit" itemValue="id" items="${productcommits}" path="/productcommits" required="true"/>
                <field:select field="productStake" id="c_com_bb_domain_CustomerProduct_productStake" itemValue="id" items="${productstakes}" path="/productstakes" required="true"/>
                <spring:message code="label_${fn:toLowerCase(fn:substringAfter('c_com_bb_domain_CustomerProduct_startDate','_'))}" htmlEscape="false" var="label"/>
                <label for="c_com_bb_domain_CustomerProduct_startDate">
                    <c:out value="${label}"/>:</label>
                <input id="c_com_bb_domain_CustomerProduct_startDate" name="startDate" type="text" value="" readonly=""/>
                <br/>
                <script src="/resources/scripts/jquery-ui/development-bundle/ui/i18n/jquery.ui.datepicker-zh-CN.js" type="text/javascript">
                    <!--jquery ui-->
                </script>
                <script>
                    $(function() {
                        $( "#c_com_bb_domain_CustomerProduct_startDate" ).datepicker({
                            dateFormat: "yy-mm-dd",
                            numberOfMonths: 1,
                            showButtonPanel: true,
                            minDate: -0, maxDate: '+12M',
                            beforeShowDay: function(date){ return [date.getDay() == 1,""]}
                        });
                    });
                </script>

            </form:update>
        </div>
    </div>
</div>


