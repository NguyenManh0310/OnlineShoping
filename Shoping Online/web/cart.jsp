<%@page import="java.util.ArrayList" %>
<%@page import="DAL.Products" %>
<%@page import="models.ProductDAO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="template/header.jsp" %>

<div id="cart">
    <div id="cart-title" style="background-color: sienna;color: white">
        <h3 >SHOPPING CART</h3>
    </div>


    <div id="cart-content">

        <div style="color: limegreen;margin-left: 5%;margin-bottom: 4%">${rmok}</div>
        <c:remove var="rmok" scope="session" />
        <div style="color: limegreen;margin-left: 5%;margin-bottom: 4%">${ODok}</div>
        <c:remove var="ODok" scope="session" />
        <div style="color: red;margin-left: 5%;margin-bottom: 4%">${cantorder}</div>
        <c:remove var="cantorder" scope="session" />
        <c:choose>    
            <c:when test="${AccSession ne null}">    

                <c:forEach items="${listcusperpage}" var="i">

                    <div class="cart-item">
                        <div class="cart-item-infor">
                            <div class="cart-item-img">
                                <img src="img/1.jpg"/>
                            </div>
                            <div class="cart-item-name">
                                <a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}">${i.getProductName()}</a>
                            </div>
                            <div class="cart-item-price">
                                ${i.getUnitPrice()} $
                            </div>
                            <c:url value="/Home" var="remove">
                                <c:param name="OrderID" value="${i.getOrderID()}"/>
                                <c:param name="pID" value="${i.getProductID()}"/>
                            </c:url>
                            <div class="cart-item-button">

                                <form action="${remove}" method="post">
                                    <button type="submit" >REMOVE</button>
                                </form>
                            </div>
                        </div>

                        <c:url value="/Home" var="minus">
                            <c:param name="minusOrderID" value="${i.getOrderID()}"/>
                            <c:param name="Quantity" value="${i.getNumber()}"/>
                            <c:param name="pID" value="${i.getProductID()}"/>
                        </c:url>
                        <c:url value="/Home" var="add">
                            <c:param name="addID" value="${i.getProductID()}"/>
                            <c:param name="pID" value="${i.getProductID()}"/>
                        </c:url>
                        <div class="cart-item-function" style="display: flex;margin: 1%;gap: 10px">
                            <form action="${minus}" method="post" >
                                <button type="submit" style="width: 20px">-</button>
                            </form>
                            <form action="${add}" method="post">
                                <button type="submit" style="width: 20px">+</button>
                            </form>

                            <input type="text" value="${i.getNumber()}" disabled/>
                        </div>
                    </div>        

                </c:forEach>
                <div id="paging" >
                    <div class="pagination">
                        <c:forEach begin="1" end="${numpage+1}" var="ii">
                            <a <c:if test="${page eq ii}"> style="background-color: sienna; color: white;"
                                                           </c:if>href="/BaiTap/cart?page=${ii}">${ii}</a>
                        </c:forEach>
                    </div>
                </div>
            </c:when>
            <c:when test="${cart ne null}">

                <c:forEach items="${listguestperpage}" var="i">
                    <div class="cart-item">
                        <div class="cart-item-infor">
                            <div class="cart-item-img">
                                <img src="img/1.jpg"/>
                            </div>
                            <div class="cart-item-name">
                                <a href="${pageContext.request.contextPath}/detail?pid=${i.getProductID()}">${i.getProductName()}</a>
                            </div>
                            <div class="cart-item-price">
                                ${i.getUnitPrice()} $
                            </div>
                            <c:url value="/Home" var="removeg">

                                <c:param name="guestrmID" value="${i.getProductID()}"/>
                            </c:url>
                            <div class="cart-item-button">

                                <form action="${removeg}" method="post">
                                    <button type="submit" >REMOVE</button>
                                </form>
                            </div>
                        </div>
                        <c:url value="/Home" var="minusg">
                            <c:param name="minuspID" value="${i.getProductID()}"/>
                        </c:url>
                        <c:url value="/Home" var="addg">
                            <c:param name="addpID" value="${i.getProductID()}"/>
                        </c:url>
                        <div class="cart-item-function" style="display: flex;margin: 1%;gap: 10px">
                            <form action="${minusg}" method="post" >
                                <button type="submit" style="width: 20px">-</button>
                            </form>
                            <form action="${addg}" method="post">
                                <button type="submit" style="width: 20px">+</button>
                            </form>
                            <input type="text" value="${i.getNumber()}" disabled/>
                        </div>
                    </div>  

                </c:forEach>
                <div id="paging">
                    <div class="pagination">
                        <c:forEach begin="1" end="${numpage+1}" var="ii">
                            <a <c:if test="${page eq ii}"> style="background-color: sienna; color: white;"
                                                           </c:if>href="/BaiTap/cart?page=${ii}">${ii}</a>
                        </c:forEach>
                    </div>
                </div>
            </c:when>
        </c:choose>
    </div>

    <div id="cart-summary">
        <div id="cart-summary-content" style="padding: 2%">Total amount: <span style="color:red">${total} $</span></div>
    </div>
    <form action="order" method="post">
        <div id="customer-info">
            <div id="customer-info-content">
                <h3>CUSTOMER INFORMATION:</h3>
                <div id="customer-info-detail">
                    <div id="customer-info-left">
                        Company Name : <input type="text" name="txtCompanyName" <c:if test="${AccSession ne null}"> value="${cus.getCompanyName()}" disabled</c:if>/><br/>
                        <div style="color: red;margin-bottom: 4%">${company}</div>
                        <c:remove var="company" scope="session" />
                        Contact Name : <input type="text" name="txtContactName" <c:if test="${AccSession ne null}"> value="${cus.getContactName()}" disabled</c:if>/><br/>
                        <div style="color: red;margin-bottom: 4%">${contactName}</div>
                        <c:remove var="contactName" scope="session" />    
                    </div>
                    <div id="customer-info-right">
                        Contact Title : <input type="text" name="txtConTitle"<c:if test="${AccSession ne null}"> value="${cus.getContactTitle()}" disabled</c:if>/><br/>
                        <div style="color: red;margin-bottom: 4%">${contactTitle}</div>
                        <c:remove var="contactTitle" scope="session" />
                        Address : <input type="text" name="txtAddress" <c:if test="${AccSession ne null}"> value="${cus.getAddress()}" disabled</c:if>/><br/>
                        <div style="color: red;margin-bottom: 4%">${address}</div>
                        <c:remove var="address" scope="session" />    
                    </div>
                </div>
                <c:if test="${AccSession ne null}">
                    <a style="text-decoration: none;color:white;font-size: 20px" 
                       href="${pageContext.request.contextPath}/profile?p=editinfo">
                        <div style="background-color: sienna;border-radius: 3px;width: 20%;padding: 1%">Edit Infomation</div>
                    </a>
                </c:if>
            </div>
        </div>
        <div id="customer-info">
            <div id="customer-info-content">
                <h3>PAYMENT METHODS:</h3>
                <div id="customer-info-payment">
                    <div>
                        <input type="radio" name="rbPaymentMethod" checked/>
                        Payment C.O.D - Payment on delivery
                    </div>
                    <div>
                        <input type="radio" name="rbPaymentMethod" disabled/>
                        Payment via online payment gateway
                    </div>
                </div>
            </div>
        </div>

        <form action="order" method="post" >

            <c:if test="${gsize ne 0 && csize ne 0}">
                <div style="padding: 2%;margin-left: 60%">
                    <div style="color: red;margin-left: 5%">${RD}</div>
                    <c:remove var="RD" scope="session" />
                    <label>Required Date : </label>
                    <input style="margin: 3%;" type="date" name="txtRequiredDate" id="txtDob"/>
                </div><br>
                <div id="cart-order" style="margin-bottom: 4%">
                    <input style="background-color: sienna;color: white;" type="submit" value="ORDER"/>
                </div>
            </c:if>     


        </form>

    </form>
</div>

</div>



<%@include file="template/footer.jsp" %>