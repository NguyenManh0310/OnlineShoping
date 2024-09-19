<%-- 
    Document   : search
    Created on : Oct 24, 2022, 8:02:38 PM
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="template/header.jsp" %>
<div id="content-left">
    <h3>CATEGORY</h3>
    <ul>
        <a href="${pageContext.request.contextPath}/category?pid=all"><li <c:if test="${all ne null}"> style="background-color: sienna;color:white;"</c:if>>All</li></a>
                <c:forEach items="${listcate}" var="i">            
            <a href="${pageContext.request.contextPath}/category?pid=${i.getCategoryID()}">
                <li <c:if test="${cateID eq i.getCategoryID()}">
                        style="background-color: sienna;
                        color: white;"
                    </c:if>>${i.getCategoryName()}</li></a>                           
                </c:forEach>
    </ul>
</div>
<div id="content-right">
    <div class="path">Search Value = "${sname}"</b></div>

    <div class="content-main" style="width: 100%;
         display: grid ;
         grid-template-columns: repeat(4,1fr);
         margin-bottom: 15px;
         gap: 10px;">
        <c:forEach items="${listsearch}" var="i">
            <div class="product">
                <a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}"><img src="img/1.jpg" width="100%"/></a>
                <div class="name"><a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}">
                        ${i.getProductName()}</a></div>
                <div class="price">${i.getUnitPrice()} $</div>
                <div><a href="">Buy now</a></div>
            </div>
        </c:forEach>
    </div>
</div>




<%@include file="template/footer.jsp" %>
