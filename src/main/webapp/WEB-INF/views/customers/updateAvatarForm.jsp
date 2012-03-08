<%--<script type="text/javascript" src="<c:url value="/scripts/jquery.js"/>"></script>--%>
<%--<script>--%>

<%--$(document).ready(function () {--%>
<%--//        $("#PromotionImageUploadForm").validate(--%>
<%--//                {--%>
<%--//                    rules:{--%>
<%--//                        image:{ accept:"(jpe?g|gif|png)", required:true }--%>
<%--//                    },--%>
<%--//--%>
<%--//                    debug:true,--%>
<%--//                    submitHandler:function (form) {--%>
<%--//                        form.submit();--%>
<%--//                    }--%>
<%--//                }--%>
<%--//        );--%>
<%--});--%>
<%--</script>--%>

<form id="customer" action="/customers" method="POST" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${customer.id}"/>
    <input type="hidden" name="username" value="yyysss"/>
    <input type="hidden" name="password" value="${customer.password}"/>
    <input type="hidden" name="email" value="saaaaaaaaaaaaaa"/>
    <input type="hidden" name="status" value="Trial"/>
    <table>
        <tr>
            <td>Image:</td>
            <td>
                <input type="file" name="avatar" multiple="true" label="Select Some Files" id="avatar" data-dojo-type="dojox.form.Uploader"/>
                <script type="text/javascript">dojo.require("dijit.form.SimpleTextarea");</script>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" name="btnUpload" value="OK"/>
            </td>
        </tr>
    </table>
</form>
