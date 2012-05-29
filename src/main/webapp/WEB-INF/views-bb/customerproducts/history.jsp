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
        $("#tabstrip").kendoTabStrip({
            animation:	{
                open: {
                    effects: "fadeIn"
                }
            }

        });
    });
</script>

<div id="main">
    <h1 id="exampleTitle" class="k-content">
        <span class="exampleIcon overviewIcon"></span>
        历史记录
    </h1>

    <div id="example" class="k-content" style="width:700px;">
        <div style="margin-bottom: 10px;"></div>
        <div id="tabstrip">
            <ul>
                <li>
                出勤历史记录
                </li>
                <li class="k-state-active">
                    每周动则赢统计
                </li>
            </ul>

            <div class="gridWrapper">
                <table id="grid-checkin-history">
                    <thead>
                    <tr>
                        <th data-field="">地点</th>
                        <th data-field="">开始日期</th>
                        <th data-field="">结束日期</th>
                        <th data-field="">时长</th>
                        <th data-field="">结束方式</th>
                        <th data-field="">是否有效</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${customercheckins}" var="checkin">
                    <tr>
                        <td>${checkin.location.name}</td>
                        <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${checkin.startDate}" type="both"/></td>
                        <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${checkin.endDate}" type="both"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${checkin.timeLengthInMinute=='-1'}">无法确定</c:when>
                                <c:otherwise>${checkin.timeLengthInMinute}分钟</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${checkin.endType=='No_Signal'}">定位信号丢失</c:when>
                                <c:otherwise>出勤完毕</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${checkin.isApproved==true}">有效</c:when>
                                <c:otherwise>否</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="gridWrapper">
                <table id="grid-weekly-history">
                    <thead>
                    <tr>
                        <th data-field="">开始日期</th>
                        <th data-field="">结束日期</th>
                        <th data-field="">承诺出勤</th>
                        <th data-field="">缺勤每天罚金</th>
                        <th data-field="">实际出勤</th>
                        <th data-field="">当周奖金</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${allWeekStatus}" var="weekStatus">
                        <tr>
                            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${weekStatus.startDate}" type="both"/></td>
                            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${weekStatus.endDate}" type="both"/></td>
                            <td>${weekStatus.customerProduct.productCommit.commits}天</td>
                            <td>${weekStatus.customerProduct.productStake.stakes}元</td>
                            <td>${weekStatus.daysCompleted}天</td>
                            <td>${weekStatus.customerProfit.amount}元</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <script>
            $(document).ready(function () {
                $("#grid-checkin-history").kendoGrid({
                    height:400,
                    scrollable:true,
                    sortable:true,
                    pageable:true
                });
            });

            $("#grid-weekly-history").kendoGrid({
                height:400,
                scrollable:true,
                sortable:true,
                pageable:true
            });

        </script>
    </div>
</div>


