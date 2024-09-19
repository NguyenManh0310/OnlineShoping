<%@include file="template/header.jsp" %>
<div id="content-left">
    <ul>
        <a href="${pageContext.request.contextPath}/admin?p=dashboard"><li>Dashboard</li></a>
        <a href="${pageContext.request.contextPath}/admin?p=orders&page=1"><li>Orders</li></a>
        <a href="${pageContext.request.contextPath}/admin?p=products&page=1"><li>Products</li></a>
        <a href="${pageContext.request.contextPath}/admin?p=customer"><li>Customers</li></a>
    </ul>
</div>
<div id="content-right">
    <div class="path-admin">ORDER DETAIL</b></div>
    <div class="content-main">
        <div id="content-main-dashboard">
            <div style="height: 100px">
                <div class="profile-order-title" style="width: 97%">
                    <div class="profile-order-title-left">
                        <div>OrderID: #${OrderID}</div>
                        <div>Order creation date: ${date.getOrderDate()}</div>

                        <c:if test="${date.getShippedDate() != null}"><div>Order shipped date: ${date.getShippedDate()}</div></c:if>
                        </div>
                        <div class="profile-order-title-right" >
                        <c:if test="${date.getShippedDate() != null}">
                            <span style="color: green;">Completed</span>
                        </c:if>
                        <c:if test="${date.getShippedDate() == null && date.getRequiredDate()!=null}">
                            <div class="dis" style="display: flex;margin-left: 55%;gap: 4%">
                                <span style="color: blue;">Pending</span>
                                <c:url value="/admin" var="adcancel">
                                    <c:param name="AdminOrderID" value="${OrderID}"/>
                                    <c:if test="${inorderdetail !=null}"><c:param name="in" value="${inorderdetail}"/></c:if>
                                </c:url>
                                <form action="${adcancel}" method="post">
                                    <button type="submit" >Cancel</button>
                                </form>
                            </div>
                        </c:if>
                        <c:if test="${date.getShippedDate() == null && date.getRequiredDate()==null}">
                            <span style="color: red;">Canceled</span>
                        </c:if>
                    </div>
                </div>
            </div>
            <c:forEach items="${listprobyOID}" var="i">
                <div class="profile-order-content" style="background-color: white;">
                    <div class="profile-order-content-col1">
                        <a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}">
                            <img src="img/2.jpg" width="100%"/></a>
                    </div>
                        <div class="profile-order-content-col2">
                            <a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}">${i.getProductName()}</a>
                        </div>
                    <div class="profile-order-content-col3">Quantity: ${i.getNumber()}</div>
                    <div class="profile-order-content-col4">${i.getUnitPrice()} $</div>
                </div>

            </c:forEach>
        </div>
    </div>
</div>


<%@include file="template/footer.jsp" %>
