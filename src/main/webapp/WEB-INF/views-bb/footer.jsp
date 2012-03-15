<%@ page contentType="text/html; charset=utf-8"%>

<div id="footer">
    <div class="floatWrap centerWrap">
        <ul>
            <li><a href="http://www.kendoui.com/">Home</a></li>
            <li><a href="http://www.kendoui.com/web">Web</a></li>
        </ul>
        <p id="copy">Copyright © 2012 联信脉动 Inc. All rights reserved.</p>
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
