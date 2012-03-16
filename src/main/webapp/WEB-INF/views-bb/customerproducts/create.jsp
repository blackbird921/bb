<%@ page contentType="text/html; charset=utf-8" %>

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
        我的动则赢
    </h1>

    <div id="exampleWrap" style="visibility: visible; ">
        <div id="example" class="k-content">
            <div class="description">
                <!--<a class="k-button" id="bookmarklet" href="">保存</a>-->
                <div style="text-align: center;">
                    <h1 style="color:#ef652a;">
                        本周剩余<span style="font-size:40px;margin-left:4px;margin-right:4px;">5</span>天，还须出勤<span style="font-size:40px;margin-left:4px;margin-right:4px;">2</span>天
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
                        <td>6天</td>
                        <td>10元</td>
                    </tr>
                    </tbody>
                </table>
                <h4>本周进度:</h4>
                <table class="grid center-text">
                    <thead>
                    <tr>
                        <th class="center-text" data-field="title" width="25%">开始日期</th>
                        <th class="center-text" data-field="title" width="25%">结束日期</th>
                        <th class="center-text" data-field="title" width="25%">已完成</th>
                        <th class="center-text" data-field="year">待完成</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>2012-01-02</td>
                        <td>2012-01-08</td>
                        <td>4天</td>
                        <td>2天</td>
                    </tr>
                    </tbody>
                </table>

                <h4>我的战果:</h4>
                <table class="grid center-text">
                    <thead>
                    <tr>
                        <th class="center-text" data-field="title" width="25%">奖金总额</th>
                        <th class="center-text" data-field="title" width="25%">奖金排名</th>
                        <th class="center-text" data-field="title" width="25%">完成率</th>
                        <th class="center-text" data-field="year">完成率排名</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1350元</td>
                        <td>第38位</td>
                        <td>90%</td>
                        <td>第99位</td>
                    </tr>
                    </tbody>
                </table>

                <!--<div class="grid_2 prefix_3">-->
                <!--<p class="xx">14s0</p>-->
                <!--</div>-->
            </div>
        </div>
    </div>
</div>
<!--/ main-->
<div id="" style="float: left;margin: 2em; text-align: center;">
    <div class="shadow">
        <img class="frame" src="/resources/images/upload/1.png" width="120px" height="120px" alt=""/>
    </div>
    <div style="position: relative;"></div>
    <div class="k-button">更换头像</div>
    <div class="k-button">邀请我的朋友</div>
</div>

