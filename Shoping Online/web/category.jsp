<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="template/header.jsp" %>

<div id="content-left">
    <h3>CATEGORY</h3>
    <ul >
        <a href="${pageContext.request.contextPath}/category?pid=0"><li <c:if test="${all ne null}"> style="background-color: sienna;color:white;"</c:if>>All</li></a>
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
    <div class="path">Category</div>
    <div style="width: 100%;
         display: grid ;
         grid-template-columns: repeat(4,1fr);
         margin-bottom: 15px;
         gap: 10px;" class="content-main">
        <c:choose>

            <c:when test = "${listperpage ne null}">
                <c:forEach items="${listperpage}" var="i">
                    <div class="product">
                        <a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}"><img src="img/1.jpg" width="100%"/></a>
                        <div class="name"><a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}">
                                ${i.getProductName()}</a></div>
                        <div class="price">${i.getUnitPrice()} $</div>
                        <div><a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}">Buy now</a></div>
                    </div>

                </c:forEach>
                </br>
                <div id="paging" style="align-items: center;justify-content: center;width: 100%">
                    <div class="pagination" style="display: flex; ">
                        <c:forEach begin="1" end="${numpage+1}" var="ii">
                            <a <c:if test="${page eq ii}"> style="background-color: sienna; color: white;"
                                                           </c:if>href="/BaiTap/category?pid=0&page=${ii}">${ii}</a>
                        </c:forEach>
                    </div>
                </div>
            </c:when>

            <c:when test = "${list ne null}">
                <c:forEach items="${list}" var="i">
                    <div class="product" >
                        <a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}"><img src="img/1.jpg" width="100%"/></a>
                        <div class="name"><a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}">
                                ${i.getProductName()}</a></div>
                        <div class="price">${i.getUnitPrice()} $</div>
                        <div><a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}">Buy now</a></div>
                    </div>

                </c:forEach>
            </c:when>


        </c:choose>

    </div>

</div>
</div>
<%@include file="template/footer.jsp" %>
