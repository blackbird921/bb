<%--<page:show id="ps_com_bb_domain_CustomerProduct" object="${customerproduct}" path="/customerproducts" z="feP/vZ/f8s6KHWAA+Slnr6sPfBM=">--%>
<%--<field:display field="customer" id="s_com_bb_domain_CustomerProduct_customer" object="${customerproduct}" z="ISiwNjHgPcnwlM4d5NJD0j9lJ5U="/>--%>
<%--<field:display field="productCommit" id="s_com_bb_domain_CustomerProduct_productCommit" object="${customerproduct}" z="gT9Y45LWv7pwLGoYgirVmbxdOao="/>--%>
<%--<field:display field="productStake" id="s_com_bb_domain_CustomerProduct_productStake" object="${customerproduct}" z="t02YhYe5WbbaetlJ09T+oFpB6yU="/>--%>
<%--<field:display date="true" dateTimePattern="${customerProduct_startdate_date_format}" field="startDate" id="s_com_bb_domain_CustomerProduct_startDate" object="${customerproduct}" z="dNjHKJ9BU/GCVg6tntx7WE4Yqqo="/>--%>
<%--<field:display date="true" dateTimePattern="${customerProduct_enddate_date_format}" field="endDate" id="s_com_bb_domain_CustomerProduct_endDate" object="${customerproduct}" z="kYXDx/T72Ki+lh0oZ9fE84Zge0I="/>--%>
<%--</page:show>--%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">
    $(document).ready(function () {


        $("#submit").click(function () {
            var validator = $("#addCardForm input[type='radio']:checked").size();
            if (validator == 0) {
                $(".status").text("请选择健身卡！").addClass("invalid");
                return false;
            }
        });


        $("#customerCardTable").kendoGrid({
            groupable:false,
            scrollable:false,
            sortable:false,
            pageable:false
        });

    });
</script>

<div id="main" style="width: 700px;">
    <h1 id="exampleTitle" class="k-content">
        <span class="exampleIcon overviewIcon"></span>
        健身卡
    </h1>

    <div id="example" class="k-content">
        <div class="description">
            <h4>我的健身卡:</h4>
            <table id="customerCardTable" class="center-text">
                <thead>
                <tr>
                    <th class="center-text" data-field="" width="10%">名称</th>
                    <th class="center-text" data-field="">介绍</th>
                    <th class="center-text" data-field="">卡号</th>
                    <th class="center-text" data-field="">&nbsp;&nbsp;获取日期&nbsp;&nbsp;</th>
                    <th class="center-text" data-field="">最后使用日期</th>
                    <th class="center-text" data-field="" width="10%">状态</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${customercards}" var="cc">
                    <tr>
                        <td>${cc.card.name}</td>
                        <td>${cc.card.description}</td>
                        <td>${cc.id}</td>
                        <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${cc.issuedDate}" type="both"/></td>
                        <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${cc.usedDate}" type="both"/></td>
                        <td>${cc.status}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <c:if test="${not empty cards}">

                <h4>获取健身卡:</h4>
                <form:form id="addCardForm" action="/customercards" method="POST" modelAttribute="customerCard" enctype="application/x-www-form-urlencoded">
                    <input type="hidden" name="customer" value="${customerId}"/>
                    <input type="hidden" name="issuedDate" value="${issuedDate}"/>
                    <input type="hidden" name="status" value="未使用"/>
                    <table id="cardTable" class="grid center-text">
                        <thead>
                        <tr>
                            <th class="center-text" data-field="" width="10%"></th>
                            <th class="center-text" data-field="" width="30%">名称</th>
                            <th class="center-text" data-field="">介绍</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${cards}" var="card">
                            <tr>
                                <td><input type="radio" name="card" value="${card.id}" /></td>
                                <td>${card.name}</td>
                                <td>${card.description}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <button id="submit" class="k-button" type="submit" style="margin-top: 10px;">&nbsp;&nbsp;&nbsp;获取&nbsp;&nbsp;&nbsp;</button>
                    <span class="status"></span>
                </form:form>
            </c:if>
        </div>
    </div>
</div>
<script type="text/javascript">
    $("#cardTable").kendoGrid({
        groupable:false,
        scrollable:false,
        sortable:false,
        pageable:false
    });
</script>

<!--/ main-->


