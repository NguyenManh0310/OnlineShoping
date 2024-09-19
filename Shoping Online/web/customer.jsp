<%@include file="template/header.jsp" %>
<div id="content-left">
    <ul>
        <a href="${pageContext.request.contextPath}/admin?p=dashboard"><li>Dashboard</li></a>
        <a href="${pageContext.request.contextPath}/admin?p=orders&page=1"><li>Orders</li></a>
        <a href="${pageContext.request.contextPath}/admin?p=products"><li>Products</li></a>
        <a href="${pageContext.request.contextPath}/admin?p=customer"><li style="background-color: sienna;color: white">
                Customers</li></a>
    </ul>
</div>
<div id="content-right">
    <c:if test="${p eq customerdetail}">
        <div class="path">LIST ORDERS</b></div>
        <div style="color: limegreen;margin-left: 5%;margin-bottom: 4%">${cancelOD}</div>
        <c:remove var="cancelOD" scope="session" />
        <div class="content-main">
            <div id="profile-content-order">
                <div style="background-color: sienna;color: white ;font-size: 20px;padding: 1%">PENDING ORDERS</div>

                <div>

                    <c:forEach items="${listOP}" var="lop">
                        <div class="profile-order-title">
                            <div class="profile-order-title-left">

                                <div>Order creation date: ${lop.getOrderDate()}</div>
                                <div>Order: <a href="#">#${lop.getOrderID()}</a></div>
                            </div>
                            <c:url value="/admin" var="cancel">
                                <c:param name="AdminOrderID" value="${lop.getOrderID()}"/>
                                <c:if test="${incusdetail !=null}">
                                    <c:param name="incus" value="${incusdetail}"/>
                                </c:if>

                            </c:url>
                            <div style="margin-left: 40%;">

                                <form action="${cancel}" method="post">
                                    <button style="height: 40px;width: 60px" type="submit" >Cancel</button>
                                </form>
                            </div>
                        </div>

                        <c:forEach items="${listpending}" var="lp">    
                            <c:if test="${lp.getOrderID() eq lop.getOrderID()}">
                                <div class="profile-order-content">
                                    <div class="profile-order-content-col1">
                                        <a href="${pageContext.request.contextPath}/detail?pid=${lp.getProductID()}"><img src="img/2.jpg" width="100%"/></a>
                                    </div>
                                    <div class="profile-order-content-col2">${lp.getProductName()}</div>
                                    <div class="profile-order-content-col3">Quantity: ${lp.getNumber()}</div>
                                    <div class="profile-order-content-col4">${lp.getUnitPrice()} $</div>
                                    <h4 style="color: red; margin-left: 2%;margin-right: 2%">Pending</h4>

                                </div>
                            </c:if>            
                        </c:forEach>
                    </c:forEach>
                </div>

                <div style="background-color: sienna;color: white ;font-size: 20px;padding: 1%">COMPLETED ORDERS</div>
                <div>
                    <c:forEach items="${listOC}" var="loc">
                        <div class="profile-order-title">
                            <div class="profile-order-title-left">
                                <div>Order creation date: ${loc.getOrderDate()}</div>
                                <div>Order shipped date: ${loc.getShippedDate()}</div>

                            </div>

                        </div>
                        <c:forEach items="${listcom}" var="lc">    
                            <c:if test="${lc.getOrderID() eq loc.getOrderID()}">
                                <div class="profile-order-content">
                                    <div class="profile-order-content-col1">
                                        <a href="${pageContext.request.contextPath}/detail?pid=${lc.getProductID()}"><img src="img/2.jpg" width="100%"/></a>
                                    </div>
                                    <div class="profile-order-content-col2">${lc.getProductName()}</div>
                                    <div class="profile-order-content-col3">Quantity: ${lc.getNumber()}</div>
                                    <div class="profile-order-content-col4">${lc.getUnitPrice()} $</div>
                                    <h4 style="color: limegreen; margin-left: 2%;margin-right: 2%">Completed</h4>

                                </div>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </div>
                <div style="background-color: sienna;color: white ;font-size: 20px;padding: 1%">CANCELED ORDER</div>
                <div>
                    <c:forEach items="${listOCA}" var="loca">
                        <div class="profile-order-title">
                            <div class="profile-order-title-left">
                                <div>Order creation date: ${loca.getOrderDate()}</div>
                            </div>
                        </div>
                        <c:forEach items="${listCA}" var="lca">    
                            <c:if test="${lca.getOrderID() eq loca.getOrderID()}">
                                <div class="profile-order-content">
                                    <div class="profile-order-content-col1">
                                        <a href="${pageContext.request.contextPath}/detail?pid=${lca.getProductID()}"><img src="img/2.jpg" width="100%"/></a>
                                    </div>
                                    <div class="profile-order-content-col2">${lca.getProductName()}</div>
                                    <div class="profile-order-content-col3">Quantity: ${lca.getNumber()}</div>
                                    <div class="profile-order-content-col4">${lca.getUnitPrice()} $</div>
                                    <h4 style="color: red; margin-left: 2%;margin-right: 2%">Canceled</h4>

                                </div>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${p ne customerdetail}">
        <div class="path-admin">CUSTOMERS LIST</b></div>
        <div class="content-main">
            <div id="content-main-dashboard">
                <div id="order-table">
                    <table id="orders" border="1">
                        <tr>
                            <th>Customer ID</th>
                            <th>Contact Name</th>
                            <th>Company Name</th>
                            <th>Contact Title</th>
                            <th>Address</th>

                        </tr>

                        <c:forEach items="${listcus}" var="ls">
                            <tr>
                                <td><a href="${pageContext.request.contextPath}/admin?p=customerdetail&cid=${ls.getCustomerID()}">#${ls.getCustomerID()}</a></td>
                                <td>${ls.getContactName()}</td>
                                <td>${ls.getCompanyName()}</td>
                                <td>${ls.getContactTitle()}</td>
                                <td>${ls.getAddress()}</td>


                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </c:if>


</div>
</div>



<%@include file="template/footer.jsp" %>
