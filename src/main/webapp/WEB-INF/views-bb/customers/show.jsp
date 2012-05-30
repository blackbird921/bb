<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="field" tagdir="/WEB-INF/tags/form/fields" %>
<%@ taglib prefix="util" tagdir="/WEB-INF/tags/util" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">

    jQuery(document).ready(function () {
        var jcrop_api, boundx, boundy;

        $('#imgCrop').Jcrop({
            onChange:showCoords,
            onSelect:showCoords,
            aspectRatio:1
        }, function () {
            // Use the API to get the real image size
            var bounds = this.getBounds();
            boundx = bounds[0];
            boundy = bounds[1];
            // Store the API in the jcrop_api variable
            jcrop_api = this;
        });

        jQuery('#cropButton').click(function () {
            var x = jQuery("#x").val();
            var y = jQuery("#y").val();
            var w = jQuery("#w").val();
            var h = jQuery("#h").val();

            if (w == 0 || h == 0) {
                alert("您还没有选择图片的剪切区域,不能进行剪切图片!");
                return;
            }
            alert("你要剪切图片的X坐标: " + x + ",Y坐标: " + y + ",剪切图片的宽度: " + w + ",高度：" + h);
            if (confirm("确定按照当前大小剪切图片吗")) {
                document.formAvatarSave.submit();
            }
        });

        function updatePreview(c) {
            if (parseInt(c.w) > 0) {
                var rx = 100 / c.w;
                var ry = 100 / c.h;

                $('#preview').css({
                    width:Math.round(rx * boundx) + 'px',
                    height:Math.round(ry * boundy) + 'px',
                    marginLeft:'-' + Math.round(rx * c.x) + 'px',
                    marginTop:'-' + Math.round(ry * c.y) + 'px'
                });
            }
        }

        function showCoords(c) {
            updatePreview(c);

            jQuery('#x').val(c.x);
            jQuery('#y').val(c.y);
            jQuery('#w').val(c.w);
            jQuery('#h').val(c.h);

            //显示剪切按键
            jQuery('#button-avatar-save').css("display", "");
        }
    });


</script>


<div id="main">
    <h1 id="exampleTitle" class="k-content">
        <span class="exampleIcon overviewIcon"></span>
        个人信息
    </h1>

    <div id="example" class="k-content">
        <div id="avatar-window1">
            <form id="customer" action="/customers/avatarUpload" method="post" enctype="multipart/form-data">
                <p>请选择你的头像文件，然后点击“上传”:
                    <br>
                    <br>
                    <input id="id_id1" name="id" type="hidden" value="1"/>
                    <input id="avatarUploadButton" label="Select Some Files" multiple="true" name="avatar" type="file"/>
                </p>
                <%--<div id="button-avatar-upload" class="k-button">上传</div>--%>
                <button id="avatarUploadSubmit" class="k-button" type="submit" style="margin-top: 10px; margin-right: 10px;">
                    上传
                </button>
            </form>
        </div>

        <div id="avatar-window2">
            <form id="form-avatarSave" name="formAvatarSave" action="/customers/avatarSave" method="post" enctype="multipart/form-data">
                图片:<img id="imgCrop" style="margin:3px;" src="/resources/images/upload/1.png"/>

                <div style="margin-top:10px;width:100px;height:100px;overflow:hidden;">
                    预览:<img src="/resources/images/upload/1.png" id="preview" alt="Preview" class="jcrop-preview"/>
                </div>
                <input id="id_id2" name="id" type="hidden" value="1"/>
                <input type="hidden" id="x" name="x"/>
                <input type="hidden" id="y" name="y"/>
                <input type="hidden" id="w" name="w"/>
                <input type="hidden" id="h" name="h"/>
                <button id="button-avatar-save" class="k-button" type="submit" style="margin-left: 110px; margin-top: 10px; margin-right: 10px;">
                    保存
                </button>
                <div id="button-avatar-cancel" class="k-button">取消</div>
            </form>
        </div>


        <div id="form-wrapper" class="k-header" style="height: 600px; width:400px;">
            <div class="k-button" style="margin-bottom: 10px;" onclick="window.location='/customers/1?form'">更改个人信息
            </div>
            <br>
            <%--<util:avatar big="false" customerId="${customer.id}" hasUploadedAvatar="${customer.hasAvatar}" id="my_avartar" showUploadButton="false"/>--%>
            <field:display field="username" id="s_com_bb_domain_Customer_username" object="${customer}" z="EOBLp1KPw4XJzed9sXi4YjJUAfI="/>
            <field:display field="password" id="s_com_bb_domain_Customer_password" object="${customer}" z="ZveFIG/fiTqO5AGPex17bRAXUoI="/>
            <field:display field="email" id="s_com_bb_domain_Customer_email" object="${customer}" z="ylqeq5TFb8QUCET+lAVZ3IRNIFQ="/>
            <%--<field:display field="status" id="s_com_bb_domain_Customer_status" object="${customer}" z="cnOcfZTqhYDoU0LcCkpAjHbLYqY="/>--%>
            <field:display field="name" id="s_com_bb_domain_Customer_name" object="${customer}" z="yKyuV5bGSZ9XzJ9BXnTeJRptJp0="/>
            <field:display field="address" id="s_com_bb_domain_Customer_address" object="${customer}" z="ago5lTP0bG+bW1X4OhGXpuBb4QE="/>
            <field:display field="city" id="s_com_bb_domain_Customer_city" object="${customer}" z="1ivWARx/R+HmF+udl0WrwiPtUYk="/>
            <field:display field="phone" id="s_com_bb_domain_Customer_phone" object="${customer}" z="/UwqxEXutA1GC/KJQo+bf0tN5pg="/>
            <div id="_s_com_bb_domain_Customer_sex_id"><label for="_sex_id">性别:
            </label><span id="_sex_id" class="box">${customer.sex}</span></div>
            <br/>

            <field:display date="true" dateTimePattern="yyyy-MM-dd" field="birthday" id="s_com_bb_domain_Customer_birthday" object="${customer}" z="saQBtvDZliazMuhY9eHbZa7hx4E="/>
            <field:display field="bio" id="s_com_bb_domain_Customer_bio" object="${customer}" z="obfCUlGiEriEPMPwlu7q4UH2yGY="/>
            <field:display date="true" dateTimePattern="${customer_registrationdate_date_format}" field="registrationDate" id="s_com_bb_domain_Customer_registrationDate" object="${customer}" z="3VI2wFzCE1V4wTg1n8tBFCk11QA="/>
            <hr>
            <field:display date="true" dateTimePattern="${customer_disablestartdate_date_format}" field="disableStartDate" id="s_com_bb_domain_Customer_disableStartDate" object="${customer}" z="H/QeG1LIM/pYftTi7kSnZxCcOLA="/>
            <field:display date="true" dateTimePattern="${customer_disableenddate_date_format}" field="disableEndDate" id="s_com_bb_domain_Customer_disableEndDate" object="${customer}" z="0SOMUK5cR1o3KZC4OGAGlHVM6zk="/>
            <field:display field="disableReason" id="s_com_bb_domain_Customer_disableReason" object="${customer}" z="tNLqroSXh+cuLBoMUn95SJwJDUQ="/>
            <%--<field:display field="avatar" id="s_com_bb_domain_Customer_avatar" object="${customer}" render="false" z="user-managed"/>--%>
            <%--<field:display field="hasAvatar" id="s_com_bb_domain_Customer_hasAvatar" object="${customer}" render="false" z="user-managed"/>--%>

        </div>
    </div>
</div>


<%--<?xml version="1.0" encoding="UTF-8" standalone="no"?>--%>
<%--<div xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:field="urn:jsptagdir:/WEB-INF/tags/form/fields" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:page="urn:jsptagdir:/WEB-INF/tags/form" xmlns:spring="http://www.springframework.org/tags" xmlns:util="urn:jsptagdir:/WEB-INF/tags/util" version="2.0">--%>
<%--<jsp:directive.page contentType="text/html;charset=UTF-8"/>--%>
<%--<jsp:output omit-xml-declaration="yes"/>--%>
<%--<page:show id="ps_com_bb_domain_Customer" object="${customer}" path="/customers" z="mVN1DJKVCeiGrdw/NaygR0fvLfs=">--%>
<%--<util:avatar big="false" customerId="${customer.id}" hasUploadedAvatar="${customer.hasAvatar}" id="my_avartar" showUploadButton="false"/>--%>
<%--<field:display field="username" id="s_com_bb_domain_Customer_username" object="${customer}" z="EOBLp1KPw4XJzed9sXi4YjJUAfI="/>--%>
<%--<field:display field="password" id="s_com_bb_domain_Customer_password" object="${customer}" z="ZveFIG/fiTqO5AGPex17bRAXUoI="/>--%>
<%--<field:display field="email" id="s_com_bb_domain_Customer_email" object="${customer}" z="ylqeq5TFb8QUCET+lAVZ3IRNIFQ="/>--%>
<%--<field:display field="status" id="s_com_bb_domain_Customer_status" object="${customer}" z="cnOcfZTqhYDoU0LcCkpAjHbLYqY="/>--%>
<%--<field:display field="customerRole" id="s_com_bb_domain_Customer_customerRole" object="${customer}" z="Vqu5WXNd0U1kNHdq8mKYeRHIpCk="/>--%>
<%--<field:display field="name" id="s_com_bb_domain_Customer_name" object="${customer}" z="yKyuV5bGSZ9XzJ9BXnTeJRptJp0="/>--%>
<%--<field:display field="address" id="s_com_bb_domain_Customer_address" object="${customer}" z="ago5lTP0bG+bW1X4OhGXpuBb4QE="/>--%>
<%--<field:display field="city" id="s_com_bb_domain_Customer_city" object="${customer}" z="1ivWARx/R+HmF+udl0WrwiPtUYk="/>--%>
<%--<field:display field="phone" id="s_com_bb_domain_Customer_phone" object="${customer}" z="/UwqxEXutA1GC/KJQo+bf0tN5pg="/>--%>
<%--<field:display field="sex" id="s_com_bb_domain_Customer_sex" object="${customer}" z="ZRQ3W6V1JDwkYkisyKcqDdyx+Ig="/>--%>
<%--<field:display date="true" dateTimePattern="${customer_birthday_date_format}" field="birthday" id="s_com_bb_domain_Customer_birthday" object="${customer}" z="saQBtvDZliazMuhY9eHbZa7hx4E="/>--%>
<%--<field:display field="bio" id="s_com_bb_domain_Customer_bio" object="${customer}" z="obfCUlGiEriEPMPwlu7q4UH2yGY="/>--%>
<%--<field:display date="true" dateTimePattern="${customer_disablestartdate_date_format}" field="disableStartDate" id="s_com_bb_domain_Customer_disableStartDate" object="${customer}" z="H/QeG1LIM/pYftTi7kSnZxCcOLA="/>--%>
<%--<field:display date="true" dateTimePattern="${customer_disableenddate_date_format}" field="disableEndDate" id="s_com_bb_domain_Customer_disableEndDate" object="${customer}" z="0SOMUK5cR1o3KZC4OGAGlHVM6zk="/>--%>
<%--<field:display field="disableReason" id="s_com_bb_domain_Customer_disableReason" object="${customer}" z="tNLqroSXh+cuLBoMUn95SJwJDUQ="/>--%>
<%--<field:display date="true" dateTimePattern="${customer_registrationdate_date_format}" field="registrationDate" id="s_com_bb_domain_Customer_registrationDate" object="${customer}" z="3VI2wFzCE1V4wTg1n8tBFCk11QA="/>--%>
<%--<field:display field="avatar" id="s_com_bb_domain_Customer_avatar" object="${customer}" render="false" z="user-managed"/>--%>
<%--<field:display field="hasAvatar" id="s_com_bb_domain_Customer_hasAvatar" object="${customer}" render="false" z="user-managed"/>--%>
<%--</page:show>--%>
<%--</div>--%>
