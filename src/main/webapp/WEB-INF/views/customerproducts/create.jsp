<%@ page contentType="text/html; charset=utf-8"%>
<%--<!doctype html public "-//w3c//dtd xhtml 1.0 transitional//en">--%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>我的空间</title>
    <link href="/resources/scripts/kendo/styles/kendo.common.min.css" rel="stylesheet">
    <link href="/resources/scripts/kendo/styles/kendo.default.min.css" rel="stylesheet">
    <!--<link href="http://demos.kendoui.com/beta/content/shared/styles/examples.css" rel="stylesheet">-->
    <link href="/resources/scripts/kendo/styles/examples.css" rel="stylesheet">
    <script src="/resources/scripts/jquery-1.7.1.min.js"></script>
    <script src="/resources/scripts/kendo/js/kendo.all.min.js"></script>
    <style type="text/css">
        .container {padding-left:10px;}
        .shadow {float:left;margin-bottom: 10px;}
        .frame {position:relative; background:#fff; padding:10px; display:block;
            -moz-box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.6);
            -webkit-box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.6);
            box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.6);
        }
        .clear {clear:left;}
    </style>
    <!--[if IE]>
    <style type="text/css">
        .container {padding-left:14px;}
        .frame {left:4px; top:4px;}
        .shadow {background:#000; margin:-4px -4px; filter:progid:DXImageTransform.Microsoft.Blur(PixelRadius='5', MakeShadow='true', ShadowOpacity='0.60');}
    </style>
    <![endif]-->
    <script type="text/javascript">
        $(document).ready(function(){
            $(".grid").kendoGrid({
                groupable: false,
                scrollable: false,
                sortable: false,
                pageable: false
            });

        });
    </script>
</head>
<body>
<div id="exceptFooterWrap">
    <div id="exceptFooter">
        <div id="header">
            <div class="floatWrap centerWrap">
                <h1><a id="logo" href="">Kendo</a></h1>
                <ul>
                    <li><a href="">首页</a></li>
                    <li class="active"><a href="">动则赢</a></li>
                    <li><a href="">关于我们</a></li>
                    <li><a href="">登录/注册</a></li>
                    <li><a id="download" href="">下载手机应用</a></li>
                </ul>
            </div>
        </div>

        <div id="flowerWrap">
            <div class="flowers"></div>
            <div id="topnavWrap" class="centerWrap">
                <ul id="topnav">
                    <li><a id="web" class="first selected" href=""><b>我的空间</b></a></li>
                    <li><a id="dataviz" class="" href=""><b>了解动则赢</b></a></li>
                    <li><a id="mobile" class="last" href="">排行榜</a></li>
                </ul>
            </div>
        </div>

        <div id="navmainWrap" class="floatWrap centerWrap hasNavigation container_12">
            <div id="navWrap">
                <ul class="nav k-widget k-reset k-header k-panelbar" data-role="panelbar">
                    <li class="k-state-active k-state-highlighted k-item k-first">
                        <span class="k-link k-header">
                            <span class="k-sprite integrationIcon"></span>
                            动则赢
                            <span class="k-icon k-arrow-up k-panelbar-collapse"></span>
                        </span>
                        <ul class="k-group">
                            <li class="k-item k-state-active k-first">
                                <a class="k-link chosen" href="">我的动则赢</a>
                            </li>
                            <li class="k-item k-state-default">
                                <a class="k-link" href="">修改计划</a>
                            </li>
                            <li class="k-item k-state-default">
                                <a class="k-link" href="">历史记录</a>
                            </li>
                            <li class="k-item k-state-default k-last">
                                <a class="k-link" href="">体验卡</a>
                            </li>
                        </ul>
                    </li>
                    <li class="k-item k-state-default">
                        <span class="k-link k-header">
                            <span class="k-sprite integrationIcon"></span>
                            我的帐户
                            <span class="k-icon k-arrow-down k-panelbar-expand"></span>
                        </span>
                        <ul class="k-group" style="display: none; ">
                            <li class="k-item k-state-default k-first">
                                <a class="k-link" href="">个人信息</a>
                            </li>
                            <li class="k-item k-state-default k-last">
                                <a class="k-link" href="">支付信息</a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
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
                                <h1 style="color:#ef652a;">本周剩余<span style="font-size:40px;margin-left:4px;margin-right:4px;">5</span>天，还须出勤<span style="font-size:40px;margin-left:4px;margin-right:4px;">2</span>天</h1>
                            </div>
                            <h4>本周出勤计划:</h4>
                            <table class="grid center-text">
                                <thead>
                                <tr>
                                    <th class="center-text"  data-field="title" width="50%">出勤承诺天数</th>
                                    <th class="center-text"  data-field="year">缺勤每天罚金</th>
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
                    <img class="frame" src="images/1.png"  width="120px" height="120px" alt="" />
                </div>
                <div style="margin-bottom: 10px;">张三</div>
                <div class="k-button">更换头像</div>
                <div class="k-button">邀请我的朋友</div>
            </div>
        </div>
    </div>

</div>
<!-- / exceptFooter -->
<!-- / exceptFooterWrap -->

<div id="footer">
    <div class="floatWrap centerWrap">
        <ul>
            <li><a href="http://www.kendoui.com/">Home</a></li>
            <li><a href="http://www.kendoui.com/web">Web</a></li>
        </ul>
        <p id="copy">Copyright © 2011 Telerik Inc. All rights reserved.</p>
    </div>
</div>

<script>
    $(".nav").kendoPanelBar({
        animation:{ open:{ effects:"fadeIn expandVertical" } },
        expand:function () {
            $(this.element).closest(".nav").siblings(".k-panelbar").each(function () {
                $(this).find(".k-link").removeClass("k-state-selected k-state-highlighted");
            });
        },
        expandMode:"single"
    });
</script>
</body>
</html>