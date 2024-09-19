<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="template/header.jsp" %>

<div id="content-left">
    <h3 style="font-weight: normal;">Welcome, ${cus.getContactName()}</h3>
    <h3>Account Management</h3>
    <ul>
        <a href="${pageContext.request.contextPath}/profile?p=personinfo"><li
                <c:if test="${p eq personinfo}">style="background-color: sienna;color:white;"</c:if>
                    >Personal information</li></a>
        </ul>
        <h3>My order</h3>
        <ul>
            <a href="${pageContext.request.contextPath}/profile?p=allorder"><li
                <c:if test="${p eq allorder }">style="background-color: sienna;color:white;"</c:if>
                    >All orders</li></a>
            <a href="${pageContext.request.contextPath}/profile?p=cancelorder"><li
                <c:if test="${p eq cancelorder}">style="background-color: sienna;color:white;"</c:if>        
                    >Canceled order</li></a>
        </ul>
    </div>
    <div id="content-right">
    <c:choose>

        <c:when test = "${p eq personinfo}">
            <div class="path">Personal information</b></div>
            <div class="content-main">
                <div id="profile-content">
                    <div class="profile-content-col">
                        <div>Company name: <br/>${cus.getCompanyName()}</div>
                        <div>Contact name: <br/>${cus.getContactName()}</div>
                        <div>
                            <a style="text-decoration: none;color:white;font-size: 20px" 
                               href="${pageContext.request.contextPath}/profile?p=editinfo">
                                <div style="background-color: sienna;border-radius: 3px;width: 40%;padding: 1%">Edit Infomation</div>
                            </a>
                        </div>
                    </div>
                    <div class="profile-content-col">
                        <div>Contact title: <br/>${cus.getContactTitle()}</div>
                        <div>Address: <br/>${cus.getAddress()}</div>
                    </div>
                    <div class="profile-content-col">
                        <div>Email: <br/>${account.getEmail()}</div>
                    </div>
                </div>
            </div>
        </c:when>
        <c:when test = "${p eq allorder}">
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
                                    <div>Order: <a href="#">#1</a></div>
                                </div>
                                <c:url value="/order" var="cancel">
                                    <c:param name="CancelOrderID" value="${lop.getOrderID()}"/>

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
                </div>
            </div>
        </c:when>
        <c:when test = "${p eq cancelorder}">
            <div class="path">CANCELED ORDER</b></div>
            <div class="content-main">
                <div id="profile-content-order">
                    <div>
                        <c:forEach items="${listOrderCancel}" var="loca">
                            <div class="profile-order-title">
                                <div class="profile-order-title-left">
                                    <div>Order creation date: ${loca.getOrderDate()}</div>
                                </div>
                            </div>
                            <c:forEach items="${listcancel}" var="lca">    
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
        </c:when>
        <c:when test="${p eq editinfo}">
            <div class="path">EDIT INFOMATION</b></div>
            <div>${ok}</div>

            <form style="margin: 2%" action="profile" method="post" class="abc">
                <div><label class="lb">Company Name :</label>
                    <input type="text" name="txtComName" placeholder="${cus.getCompanyName()}"/></br>
                </div>
                <div><label class="lb">Contact Name :</label>
                    <input type="text" name="txtConName" placeholder="${cus.getContactName()}"/></br>
                </div>
                <label class="lb">Contact Title :</label>
                <input type="text" name="txtConTitle" placeholder="${cus.getContactTitle()}"/></br>
                <div>
                    <label class="lb">Address :</label>
                    <input type="text" name="txtAddress" placeholder="${cus.getAddress()}"/></br>
                </div>
                <input type="submit" value="Change" style="width: 10%;padding: 1%">
            </form>
            <style>
                .lb{

                    display: inline-block;
                    width: 15%;
                    margin-bottom: 15px;
                }
                .abc input[type="text"]{
                    width: 30%;
                }

            </style>
        </c:when>
        <c:otherwise>

        </c:otherwise>
    </c:choose>

</div>
<%@include file="template/footer.jsp" %>
