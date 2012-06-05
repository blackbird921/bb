<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="flowerWrap">
    <div class="flowers"></div>
    <div id="topnavWrap" class="centerWrap">
        <ul id="topnav">
            <li><a href="/customers?create" id="reg1" class="first <c:if test='${customerroles!=null}'>selected</c:if>">
                1. 注册<img src="/resources/images/arrow_right.png" height="35px" class="icon"/> </a></li>
            <li><a href="/customerproducts/create" id="reg2" class=" <c:if test='${futurecustomerproduct!=null}'>selected</c:if>" >2. 设定出勤计划 <img src="/resources/images/arrow_right.png" height="35px" class="icon"/></a></li>
            <li><a href="#" id="reg3" class="" href="">3. 设定奖金账户 <img src="/resources/images/arrow_right.png" height="35px" class="icon"/></a></li>
            <li><a href="/customercards/create" id="reg4" class="last <c:if test='${customerCard!=null}'>selected</c:if>">4. 获取健身卡<img src="/resources/images/arrow_right.png" height="35px" class="icon"/></a></li>
        </ul>
    </div>
</div>
