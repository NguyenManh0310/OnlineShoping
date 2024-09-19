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
    <div class="path-admin">CREATE A NEW PRODUCT</b></div>
    <div class="content-main">
        <c:url value="/admin" var="create">
            <c:param name="p" value="createproduct"/>
        </c:url>
        <form action="${create}" method="post" id="content-main-product">
            <div class="content-main-1">
                <label>Product name (*):</label><br/>
                <input type="text" name="txtProductName" id=""><br/>
                <span class="msg-error">${msgpName}</span><br/>
                <label>Unit price:</label><br/>
                <input type="text" name="txtUnitPrice" id=""><br/>
                <label>Quantity per unit:</label><br/>
                <input type="text" name="txtQuantityPerUnit" id=""><br/>
                <label>Units in stock (*):</label><br/>
                <input type="text" name="txtUnitsInStock" id=""><br/>
                <span class="msg-error">${msgU}</span><br/>
            </div>
            <div class="content-main-1">
                <label>Category (*):</label><br/>
                <select name="ddlCategory">
                    <c:forEach items="${listcate}" var="lc">
                        <option value="${lc.getCategoryID()}">${lc.getCategoryName()}</option>
                    </c:forEach>
                </select>
                <br/>
                
                <label>Reorder level:</label><br/>
                <input type="text" name="txtReorderLevel" id="" disabled><br/>
                <label>Units on order:</label><br/>
                <input type="text" name="txtUnitsOnOrder" id="" disabled><br/>
                <label>Discontinued:</label><br/>
                <input type="checkbox" name="chkDiscontinued" id=""><br/>
                <input type="submit" value="Save"/>
            </div>
        </form>
    </div>
</div>

<%@include file="template/footer.jsp" %>
