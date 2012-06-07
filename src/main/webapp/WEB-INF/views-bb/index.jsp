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
        $("#button-register").click(function () {
            window.location="/register/form"
        });
    });
</script>
<style type="text/css">
    #example {
        max-width: 400px;
        margin-top: 30px;
        margin-left: auto;
        margin-right: auto;
        float: none;
    !important
    }

    img.step{
        vertical-align: middle;
        margin-right: 10px;
        margin-top: 6px;
    }
</style>
<h1 class="content-title">
    <span class="exampleIcon overviewIcon"></span>
    忘记密码？
</h1>

<div id="example" class="k-content frame">
    <h2 class="content-title">
        如何成为健身达人？
    </h2>
    <h1>
        <br>
        <br>
        <img class="step" src="/resources/images/1-medium.png" alt="" >选择您的健身计划。
       <br>
        <img class="step" src="/resources/images/2-medium.png" alt="" >在健身馆用您的手机记录出勤。
        <br>
        <div style="float:left;"><img class="step" src="/resources/images/3-medium.png" alt="" ></div>
        <div style="width:280px; display: inline;"><div style="margin-top:25px;"></div>每个周末，只要您按照计划完成了出勤，奖金会自动分配到您的账户。</div>
    </h1>

    <div style="margin-left: 80px; margin-top: 60px; margin-right: 10px; ">
    <button id="button-register" style="padding:0 30px; background-color: #75C121;"  class="k-button">
        立即注册
    </button>
    </div>

    <div style="text-align: left; margin-top: 30px;">
    </div>
</div>

