<%@page import="DAL.Account" %>
<%@page import="models.CategoryDAO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="template/header.jsp" %>
<div id="content-left">
    <h3>CATEGORY</h3>
    <ul>

        <a href="${pageContext.request.contextPath}/category?pid=0&page=1">
            <li <c:if test="${all ne null}"> style="background-color: sienna;color:white;"</c:if>>All</li></a>
                <c:forEach items="${listcate}" var="i">
            <a href="${pageContext.request.contextPath}/category?pid=${i.getCategoryID()}">
                <li>${i.getCategoryName()}</li></a>
                </c:forEach>

    </ul>
</div>

<div id="content-right">
    <div class="path">Hot</b></div>
    <div class="content-main">
        <c:forEach items="${listdoid}" var="i">    
            <div class="product">
                <a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}"><img src="img/1.jpg" width="100%"/></a>
                <div class="name"><a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}">${i.getProductName()}</a></div>
                <div class="price">${i.getUnitPrice()} $</div>
                <div><a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}">Buy now</a></div>
            </div>
        </c:forEach>    
    </div>
    <div class="path">Best Sale</b></div>
    <div class="content-main">
        <c:forEach items="${listdis}" var="i">
            <div class="product">
                <a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}"><img src="img/1.jpg" width="100%"/></a>
                <div class="name"><a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}">${i.getProductName()}</a></div>
                <div class="price">${i.getUnitPrice()} $</div>
                <div><a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}">Buy now</a></div>
            </div>
        </c:forEach>    
    </div>
    <div class="path">New Product</b></div>

    <div class="content-main" style="width: 100%;
         display: grid ;
         grid-template-columns: repeat(4,1fr);
         margin-bottom: 15px;
         gap: 10px;">
        <c:forEach items="${listperpage}" var="i">
            <div class="product">
                <a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}"><img src="img/1.jpg" width="100%"/></a>
                <div class="name"><a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}">
                        ${i.getProductName()}</a></div>
                <div class="price">${i.getUnitPrice()} $</div>
                <div><a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}">Buy now</a></div>
            </div>
        </c:forEach>
    </div>
    <%@include file="paging.jsp" %>

</div>
<%@include file="template/footer.jsp" %>
