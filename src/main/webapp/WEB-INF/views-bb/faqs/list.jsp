<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="field" tagdir="/WEB-INF/tags/form/fields" %>
<%@ taglib prefix="util" tagdir="/WEB-INF/tags/util" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style type="text/css">
    .faqWrapper {
        margin-left: 100px;
        width: 800px;
        float: left;
    }

    .faqEntryEvenfalse, .faqEntryEventrue {
        margin-top: 30px;
        width: 350px;
        float: left;
        display: block;
    }

    .faqEntryEvenfalse {
        clear: left;
        margin-right: 100px;
    }

</style>
<div class="faqWrapper">
    <c:forEach items="${faqs}" var="faq" varStatus="status">
        <div class="faqEntryEven${status.count%2==0}">
            <h4><c:out value="${faq.question}"></c:out></h4>

            <div style="color: black; margin: 10px 0;">
                    ${faq.answer}
            </div>
        </div>
    </c:forEach>
</div>


