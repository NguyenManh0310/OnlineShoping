
<%@include file="template/header.jsp" %>
<div id="content-left">
    <ul>
        <a href="${pageContext.request.contextPath}/admin?p=dashboard"><li>Dashboard</li></a>
        <a href="${pageContext.request.contextPath}/admin?p=orders&page=1"><li style="background-color: sienna;color: white">
                Orders</li></a>
        <a href="${pageContext.request.contextPath}/admin?p=products&page=1"><li>Products</li></a>
        <a href="${pageContext.request.contextPath}/admin?p=customer"><li>Customers</li></a>
    </ul>
</div>

<div id="content-right">
    <div class="path-admin">ORDERS LIST</b></div>
    <div class="content-main">
        <div id="content-main-dashboard">
            <div id="order-title">
                <div style="color: red;margin-left: 5%;margin-bottom: 4%">${search}</div>
                <c:remove var="search" scope="session" />
                <b>Filter by Order date:</b>
                <c:url value="/admin" var="allorder">
                    <c:param name="p" value="orders"/>
                    <c:param name="page" value="1"/>
                    <c:if test="${param.page!=null}">
                        <c:param name="page" value="${param.page}"/>
                    </c:if>

                </c:url> 
                <form action="${allorder}" method="get">
                    From: <input type="date" name="StartDate" value="${param.StartDate}"/>
                    To: <input type="date" name="EndDate" value="${param.EndDate}"/>
                    <input type="submit" value="Filter">
                </form>
                <!-- msg -->
                <div style="color: red;margin-left: 5%;margin-bottom: 4%">${msg}</div>
                <c:remove var="msg" scope="session" />
            </div>
            <div id="order-table">
                <table id="orders">
                    <tr>
                        <th>OrderID</th>
                        <th>OrderDate</th>
                        <th>RequiredDate</th>
                        <th>ShippedDate</th>
                        <th>Employee</th>
                        <th>Customer</th>
                        <th>Freight($)</th>
                        <th>Status</th>
                    </tr>

                    <c:forEach items="${listperpage}" var="ls">
                        <tr>
                            <td><a href="${pageContext.request.contextPath}/admin?p=orderdetail&oid=${ls.getOrderID()}">#${ls.getOrderID()}</a></td>
                            <td>${ls.getOrderDate()}</td>
                            <td>${ls.getRequiredDate()}</td>
                            <td>${ls.getShippedDate()}</td>
                            <td>${ls.getEmployeeName()}</td>
                            <td>${ls.getCustomerName()}</td>
                            <td>${ls.getFreight()}</td>
                            <c:choose>
                                <c:when test="${ls.getRequiredDate()eq null}">
                                    <td style="color: red">Canceled</td>
                                </c:when>
                                <c:when test="${ls.getShippedDate() eq null}"> 
                                    <c:url value="/admin" var="adcancel">
                                        <c:param name="AdminOrderID" value="${ls.getOrderID()}"/>

                                    </c:url>
                                    <td style="display: flex;gap: 5%">
                                        <h4 style="color: blue">Pending</h4> 
                                        <form action="${adcancel}" method="post">
                                            <button type="submit" >Cancel</button>
                                        </form>
                                    </td>
                                </c:when>

                                <c:otherwise><td style="color: green">Completed</td></c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div id="paging" >
                <div class="pagination">

                    <c:forEach begin="1" end="${numpage+1}" var="ii">
                        <c:url value="/admin" var="ps">

                            <c:if test="${param.StartDate==null and param.EndDate==null}">
                                <c:param name="p" value="orders"/>
                            </c:if>
                            <c:param name="page" value="${ii}"/>

                            <c:if test="${param.StartDate!=null}"><c:param name="StartDate" value="${param.StartDate}"/> </c:if>
                            <c:if test="${param.EndDate!=null}"><c:param name="EndDate" value="${param.EndDate}"/> </c:if>
                        </c:url>
                        <a <c:if test="${page eq ii}"> style="background-color: sienna; color: white;"
                                                       </c:if>href="${ps}">${ii}</a>
                    </c:forEach>
                </div>
            </div>

        </div>
    </div>
</div>

<%@include file="template/footer.jsp" %>