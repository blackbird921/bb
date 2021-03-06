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
        我的健身计划
    </h1>

    <div id="example" class="k-content" style="margin-top: 15px;">
        <div class="description">
            <!--<a class="k-button" id="bookmarklet" href="">保存</a>-->
            <c:choose>
                <c:when test="${newUserStartDate!=null}">
                    <div style="text-align: center;">
                        <h1 style="color:#ef652a;">
                            我的健身计划将于<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${newUserStartDate}" type="both"/>正式开始
                        </h1>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${isOnVacation==true}">
                            <div style="text-align: center;">
                                <h1 style="color:#ef652a;">
                                    当前处于休假状态
                                </h1>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div style="text-align: center;">
                                <h1 style="color:#ef652a;">
                                    本周剩余<span style="font-size:40px;margin-left:4px;margin-right:4px;">${weekstatus.daysLeft}</span>天，
                                    还须出勤<span style="font-size:40px;margin-left:4px;margin-right:4px;">${weekstatus.daysToComplete}</span>天
                                </h1>
                            </div>
                            <h4>本周出勤计划:</h4>
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
                            <h4>本周进度:</h4>
                            <table class="grid center-text">
                                <thead>
                                <tr>
                                    <th class="center-text" data-field="startDate" width="25%">开始日期</th>
                                    <th class="center-text" data-field="endDate" width="25%">结束日期</th>
                                    <th class="center-text" data-field="completed" width="25%">已完成</th>
                                    <th class="center-text" data-field="todo">待完成</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td><fmt:formatDate value="${weekstatus.startDate}"/></td>
                                    <td><fmt:formatDate value="${weekstatus.endDate}"/></td>
                                    <td>${weekstatus.daysCompleted}天</td>
                                    <td>${weekstatus.daysToComplete}天</td>
                                </tr>
                                </tbody>
                            </table>

                        </c:otherwise>
                    </c:choose>

                    <h4>我的战果:</h4>
                    <table class="grid center-text">
                        <thead>
                        <tr>
                            <th class="center-text" data-field="" width="25%">奖金总额</th>
                            <th class="center-text" data-field="" width="25%">奖金总排名</th>
                            <th class="center-text" data-field="" width="25%">总完成率</th>
                            <th class="center-text" data-field="">完成率总排名</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${customerreport.bonusTotal}元</td>
                            <td>第${customerreport.bonusRank}位</td>
                            <td>${customerreport.checkinPercentage}%</td>
                            <td>第${customerreport.checkinRank}位</td>
                        </tr>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>

            <!--<div class="grid_2 prefix_3">-->
            <!--<p class="xx">14s0</p>-->
            <!--</div>-->
        </div>
    </div>
</div>
<!--/ main-->


