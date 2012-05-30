<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="" style="float: left;margin: 1em; text-align: center;">

    <div class="shadow">
        <img class="frame" src="/resources/images/upload/1.png" width="120px" height="120px" alt=""/>
    </div>
    <div style="position: relative;"></div>

    <div id="avatarChange" class="k-button">更换头像</div>
    <div class="k-button">邀请我的朋友</div>


    <script>

        $(document).ready(function () {
            var upload = $("#avatar-window1"),
                    cut = $("#avatar-window2"),
                    avatarChange = $("#avatarChange"),
                    avatarUploadSubmit=$("#avatarUploadSubmit");

            avatarUploadSubmit.hide();

            var onFileSelect = function(e) {
                avatarUploadSubmit.show();
            };

            $("#avatarUploadButton").kendoUpload({
                multiple: false,
                localization:{select:'选择文件', remove:'删除'},
                select: onFileSelect
            });


            upload.hide();
            cut.hide();


            <c:if test="${avatarCut==true}">
            upload.hide();
            cut.show();
            </c:if>

            avatarChange.bind("click", function () {
                upload.show();
                upload.data("kendoWindow").open();
            });

            if (!upload.data("kendoWindow")) {
                upload.kendoWindow({
                    width:"500px",
                    actions:["Close"],
                    modal:true,
                    title:"更换头像"
                });
            }

            if (!cut.data("kendoWindow")) {
                cut.kendoWindow({
                    width:"500px",
                    actions:["Close"],
                    modal:true,
                    title:"裁剪头像"
                });
            }

            $("#button-avatar-cancel").click(function (e) {
                cut.data("kendoWindow").close();
            });




        });
    </script>
</div>