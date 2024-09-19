<%-- 
    Document   : paging
    Created on : Oct 19, 2022, 9:38:52 PM
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="paging">
    <div class="pagination">
        <c:forEach begin="1" end="${numpage+1}" var="i">
            <a <c:if test="${page eq i}"> style="background-color: sienna; color: white;"
               </c:if>href="/BaiTap/Home?page=${i}">${i}</a>
        </c:forEach>
    </div>
</div>
