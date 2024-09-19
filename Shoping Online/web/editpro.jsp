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
    <div class="path-admin">EDIT PRODUCT</b></div>
    <div class="content-main">

        <c:url value="/admin" var="edit">
            <c:param name="p" value="editproduct"/>
        </c:url>
        <form action="${edit}" method="post" id="content-main-product">
            <div class="content-main-1">

                <label>Product name (*):</label><br/>
                <input type="text" name="txtProductName" value="${pro.getProductName()}"><br/>
                <label>Unit price:</label><br/>
                <input type="text" name="txtUnitPrice" value="${pro.getUnitPrice()}"><br/>
                <span class="msg-error">${unitok}</span><br/>
                <label>Quantity per unit:</label><br/>
                <input type="text" name="txtQuantityPerUnit" value="${pro.getQuantityPerUnit()}"><br/>
                <label>Units in stock (*):</label><br/>
                <input type="text" name="txtUnitsInStock" value="${pro.getUnitsInStock()}"><br/>
                <span class="msg-error">${unitinok}</span><br/>
            </div>
            <div class="content-main-1">
                <label>Category (*):</label><br/>
                <select name="ddlCategory" >
                    <c:forEach items="${listcate}" var="lc">
                        <option value="${lc.getCategoryID()}" <c:if test="${lc.getCategoryID() eq pro.getCategoryID()}">selected</c:if>>
                            ${lc.getCategoryName()}</option>
                        </c:forEach>
                </select>
                <br/>

                <label>Reorder level:</label><br/>
                <input type="text" name="txtReorderLevel" value="${pro.getReorderLevel()}" id="" disabled><br/>
                <label>Units on order:</label><br/>
                <input type="text" name="txtUnitsOnOrder" value="${pro.getUnitsOnOrder()}" id="" disabled><br/>
                <label>Discontinued:</label><br/>
                <input type="checkbox" name="chkDiscontinued" <c:if test="${pro.isDiscontinued() eq true}">checked</c:if> disabled/><br/>
                    <input type="submit" value="Save"/><br/>
                    <div style="color: green;margin-left: 5%;margin-bottom: 4%">${updatepro}</div>
                    <c:remove var="updatepro" scope="session" />
            </div>
        </form>

    </div>
</div>

<%@include file="template/footer.jsp" %>

