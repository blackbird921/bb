<%@ page contentType="text/html; charset=utf-8"%>

<div id="footer">
    <div class="floatWrap centerWrap">
        <ul>
            <li><a href="">工作机会</a></li>
            <li><a href="">联系我们</a></li>
        </ul>
        <p id="copy" style="color: white">Copyright © 2012 联信脉动 Inc. 京ICP备11111111号</p>
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
