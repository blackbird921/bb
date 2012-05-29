<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="field" tagdir="/WEB-INF/tags/form/fields" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<script type="text/javascript">
</script>


<div id="main" style=" width: 700px;">
    <h1 id="exampleTitle" class="k-content">
        <span class="exampleIcon overviewIcon"></span>
        账户信息
    </h1>

    <div id="example" class="k-content">

        <div style="text-align: center;">
            <h3 style="color:#ef652a;">
                当前账户余额: ${accountInfo.balance}元
            </h3>
            <div class="k-button" style="margin-bottom: 10px; margin-right: 10px;" onclick="alert('本功能开发中。')">充值</div>
            <div class="k-button" style="margin-bottom: 10px;" onclick="alert('本功能开发中。')">提取</div>
        </div>
        <div class="gridWrapper" style="margin-top: 20px;">
            <table id="grid-account-history">
                <thead>
                <tr>
                    <th data-field="">日期</th>
                    <th data-field="">项目</th>
                    <th data-field="">奖金</th>
                    <th data-field="">入账金额</th>
                    <th data-field="">出帐金额</th>
                    <th data-field="">账户余额</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${accountInfo.items}" var="item">
                    <tr>
                        <td><span style="font-size: 8px;"><fmt:formatDate pattern="yy/MM/dd HH:mm" value="${item.timestamp}" type="both"/></span></td>
                        <td><c:choose>
                            <c:when test="${item.bonus!=0}">
                                动则赢奖罚金
                            </c:when>
                            <c:otherwise>
                                ${item.name}
                            </c:otherwise>
                        </c:choose></td>
                        <td><c:if test="${item.bonus!=0}">${item.bonus}</c:if></td>
                        <td><c:if test="${item.inAmount!=0}">${item.inAmount}</c:if></td>
                        <td><c:if test="${item.outAmount!=0}">${item.outAmount}</c:if></td>
                        <td>${item.balance}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <script>
        $("#grid-account-history").kendoGrid({
            scrollable:true,
            sortable:true,
            pageable:true,
            filterable:false

        });

    </script>
</div>

