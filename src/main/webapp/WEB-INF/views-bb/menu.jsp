<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
                    <a class="k-link chosen" href="/customerproducts/show">我的动则赢</a>
                </li>
                <li class="k-item k-state-default">
                    <a class="k-link" href="/customerproducts?form">修改计划</a>
                </li>
                <li class="k-item k-state-default">
                    <a class="k-link" href="/customerproducts/hist">历史记录</a>
                </li>
                <li class="k-item k-state-default k-last">
                    <a class="k-link" href="/customerproducts/card">健身卡</a>
                </li>
            </ul>
        </li>
        <li class="k-state-active  k-state-highlighted k-item k-state-default">
                        <span class="k-link k-header">
                            <span class="k-sprite integrationIcon"></span>
                            我的帐户
                            <span class="k-icon k-arrow-down k-panelbar-expand"></span>
                        </span>
            <ul class="k-group">
                <li class="k-item k-state-default k-first">
                    <a class="k-link" href="/customers/show">个人信息</a>
                </li>
                <li class="k-item k-state-default">
                    <a class="k-link" href="/customers?vacationform">请假</a>
                </li>
                <li class="k-item k-state-default k-last">
                    <a class="k-link" href="/customers/account">账户信息</a>
                </li>
            </ul>
        </li>
    </ul>
</div>

